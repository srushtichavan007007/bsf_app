package com.bsf.app.controller;

import com.bsf.app.dto.AccountRequest;
import com.bsf.app.exception.DuplicateAccountException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Account;
import com.bsf.app.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test cases for the AccountController class
 *
 * @author Srushti.Chavan
 */
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private static final String API_URL = "/api/account";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldSuccessfullyCreateAccount() throws Exception {
        //given
        Account account = anAccount();
        when(accountService.save(isA(AccountRequest.class)))
                .thenReturn(account);

        //when
        AccountRequest accountRequest = anAccountRequest();

        this.mockMvc.perform(put(API_URL + "/create").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountRequest))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(asJsonString(account))));

        //then
        verify(accountService, times(1)).save(isA(AccountRequest.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsDuplicate() throws Exception {

        //given
        when(accountService.save(isA(AccountRequest.class)))
                .thenThrow(DuplicateAccountException.class);


        //when
        AccountRequest accountRequest = anAccountRequest();

        this.mockMvc.perform(put(API_URL + "/create").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountRequest))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Duplicate Account!")));

        //then
        verify(accountService, times(1)).save(isA(AccountRequest.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void shouldSuccessfullyGetAccount() throws Exception {
        //given
        Account account = anAccount();
        when(accountService.find(isA(String.class)))
                .thenReturn(account);

        //when
        this.mockMvc.perform(get(API_URL + "/get/202206101986")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(account))));

        //then
        verify(accountService, times(1)).find(isA(String.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsInvalid() throws Exception {
        //given
        when(accountService.find(isA(String.class)))
                .thenThrow(InvalidAccountException.class);

        //when
        this.mockMvc.perform(get(API_URL + "/get/202206101986")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid Account!")));

        //then
        verify(accountService, times(1)).find(isA(String.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void shouldSuccessfullyGetAllAccounts() throws Exception {
        //given
        when(accountService.findAll()).thenReturn(anAccountList());

        //when
        this.mockMvc.perform(get(API_URL + "/all")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        //then
        verify(accountService, times(1)).findAll();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void shouldSuccessfullyGetEmptyAccountList() throws Exception {
        //given
        when(accountService.findAll()).thenReturn(Collections.emptyList());

        //when
        this.mockMvc.perform(get(API_URL + "/all")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        //then
        verify(accountService, times(1)).findAll();
        verifyNoMoreInteractions(accountService);
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }

    private AccountRequest anAccountRequest() {
        return AccountRequest.builder()
                .name("Srushti")
                .number("202206101986")
                .balance(340.50).build();
    }

    private Account anAccount() {
        return Account.builder()
                .id(1l)
                .name("Srushti")
                .number("202206101986")
                .balance(340.50).build();

    }

    private List<Account> anAccountList() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(anAccount());
        accounts.add(new Account(2l,"Soham","721872819", 1000.0));
        accounts.add(new Account(3l, "Vaishali", "90908201", 534.0));
        return accounts;
    }
}

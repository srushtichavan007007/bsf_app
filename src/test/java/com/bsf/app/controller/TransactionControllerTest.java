package com.bsf.app.controller;

import com.bsf.app.dto.TransactionRequest;
import com.bsf.app.exception.InsufficientFundException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Transaction;
import com.bsf.app.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test cases for the TransactionController class
 *
 * @author Srushti.Chavan
 */
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldSuccessfullyTransferAmount() throws Exception {
        //given
        Transaction transaction = aTransaction();
        when(transactionService.transfer(isA(TransactionRequest.class)))
                .thenReturn(transaction);

        //when
        this.mockMvc.perform(put("/api/transfer").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(aTransactionRequest()))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isAccepted());

        //then
        verify(transactionService, times(1)).transfer(isA(TransactionRequest.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsInvalid() throws Exception {
        //given
        when(transactionService.transfer(isA(TransactionRequest.class)))
                .thenThrow(InvalidAccountException.class);

        //when
        this.mockMvc.perform(put("/api/transfer").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(aTransactionRequest()))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid Account!")));

        //then
        verify(transactionService, times(1)).transfer(isA(TransactionRequest.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenInsufficientFunds() throws Exception {
        //given
        when(transactionService.transfer(isA(TransactionRequest.class)))
                .thenThrow(InsufficientFundException.class);

        //when
        this.mockMvc.perform(put("/api/transfer").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(aTransactionRequest()))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Insufficient Fund!")));

        //then
        verify(transactionService, times(1)).transfer(isA(TransactionRequest.class));
        verifyNoMoreInteractions(transactionService);
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }

    private TransactionRequest aTransactionRequest() {
        return TransactionRequest.builder()
                .fromAccount("202223111987")
                .toAccount("202206101986")
                .amount(49.75).build();
    }

    private Transaction aTransaction() {
        return Transaction.builder()
                .id(1l)
                .fromAccount("202223111987")
                .toAccount("202206101986")
                .amount(49.75)
                .timestamp(new Timestamp(System.currentTimeMillis())).build();
    }
}

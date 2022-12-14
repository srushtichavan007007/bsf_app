package com.bsf.app.service;

import com.bsf.app.dto.AccountRequest;
import com.bsf.app.exception.DuplicateAccountException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Account;
import com.bsf.app.repository.AccountRepository;
import com.bsf.app.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * Test cases for the AccountServiceImpl class
 *
 * @author Srushti.Chavan
 */
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    /**
     * Test Values
     */
    private static final String TEST_ACCOUNT_NUMBER = "202206101986";
    private static final String TEST_ACCOUNT_NAME = "Srushti";
    private static final Double TEST_ACCOUNT_BALANCE = 340.50;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void shouldSuccessfullySaveAccount() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.empty());
        when(accountRepository.save(isA(Account.class))).thenReturn(anAccount());

        //when
        Account account = accountService.save(anAccountRequest());

        //then
        assertNotNull("Account should be non null", account);
        assertEquals("Account number should match expected value", TEST_ACCOUNT_NUMBER, account.getNumber());
        assertEquals("Account name should match expected value", TEST_ACCOUNT_NAME, account.getName());
        assertEquals("Account balance should match expected value", TEST_ACCOUNT_BALANCE, account.getBalance());

        verify(accountRepository, times(1)).findByNumber(isA(String.class));
        verify(accountRepository, times(1)).save(isA(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsDuplicate() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.of(anAccount()));

        //when, then
        Assertions.assertThrows(DuplicateAccountException.class,
                () -> {
                    accountService.save(anAccountRequest());
                });

        verify(accountRepository, times(1)).findByNumber(isA(String.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldSuccessfullyFindAccount() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.of(anAccount()));

        //when
        Account account = accountService.find("202206101986");

        //then
        assertNotNull("Account should be non null", account);
        assertEquals("Account name should match expected value", TEST_ACCOUNT_NAME, account.getName());
        assertEquals("Account balance should match expected value", TEST_ACCOUNT_BALANCE, account.getBalance());

        verify(accountRepository, times(1)).findByNumber(isA(String.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsInvalid() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(InvalidAccountException.class,
                () -> {
                    accountService.find("123");
                });

        //then
        verify(accountRepository, times(1)).findByNumber(isA(String.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldSuccessfullyFindAllAccounts() throws Exception {
        //given
        when(accountRepository.findAll()).thenReturn(anAccountList());

        //when
        List<Account> accounts = accountService.findAll();

        //then
        assertEquals("List must contain only 1 account", 1, accounts.size());
        verify(accountRepository, times(1)).findAll();
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldSuccessfullyGetEmptyList() throws Exception {
        //given
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<Account> accounts = accountService.findAll();

        //then
        assertEquals("List must contain only 0 account", 0, accounts.size());
        verify(accountRepository, times(1)).findAll();
        verifyNoMoreInteractions(accountRepository);
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
        return accounts;
    }
}

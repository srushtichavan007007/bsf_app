package com.bsf.app.service;

import com.bsf.app.dto.TransactionRequest;
import com.bsf.app.exception.InsufficientFundException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Account;
import com.bsf.app.model.Transaction;
import com.bsf.app.repository.AccountRepository;
import com.bsf.app.repository.TransactionRepository;
import com.bsf.app.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * Test cases for the TransactionServiceImpl class
 *
 * @author Srushti.Chavan
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    /**
     * Test Values
     */
    private static final String TEST_FROM_ACCOUNT_NUMBER = "202223111987";
    private static final String TEST_TO_ACCOUNT_NUMBER = "202206101986";
    private static final Double TEST_TRANSFER_AMOUNT = 49.75;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void shouldSuccessfullyTransfer() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.of(anAccount()));
        when(accountRepository.save(isA(Account.class))).thenReturn(anAccount());
        when(transactionRepository.save(isA(Transaction.class))).thenReturn(aTransaction());

        //when
        Transaction transaction = transactionService.transfer(aTransactionRequest());

        //then
        assertNotNull("Transaction should be non null", transaction);
        assertEquals("From Account number should match expected value", TEST_FROM_ACCOUNT_NUMBER, transaction.getFromAccount());
        assertEquals("To Account number should match expected value", TEST_TO_ACCOUNT_NUMBER, transaction.getToAccount());
        assertEquals("Amount should match expected value", TEST_TRANSFER_AMOUNT, transaction.getAmount());

        verify(accountRepository, times(2)).findByNumber(isA(String.class));
        verify(accountRepository, times(2)).save(isA(Account.class));
        verify(transactionRepository, times(1)).save(isA(Transaction.class));
        verifyNoMoreInteractions(accountRepository, transactionRepository);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenNumberIsInvalid() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.empty());

        //when
        Assertions.assertThrows(InvalidAccountException.class,
                () -> {
                    transactionService.transfer(aTransactionRequest());
                });

        //then
        verify(accountRepository, times(1)).findByNumber(isA(String.class));
        verifyNoInteractions(transactionRepository);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void shouldThrowCorrectExceptionWhenInsufficientAmount() throws Exception {
        //given
        when(accountRepository.findByNumber(isA(String.class))).thenReturn(Optional.of(anAccount()));

        //when
        Assertions.assertThrows(InsufficientFundException.class,
                () -> {
                    transactionService.transfer(anInsufficientTransactionRequest());
                });

        //then
        verify(accountRepository, times(2)).findByNumber(isA(String.class));
        verifyNoInteractions(transactionRepository);
        verifyNoMoreInteractions(accountRepository);
    }

    private TransactionRequest aTransactionRequest() {
        return TransactionRequest.builder()
                .fromAccount("202223111987")
                .toAccount("202206101986")
                .amount(49.75).build();
    }

    private TransactionRequest anInsufficientTransactionRequest() {
        return TransactionRequest.builder()
                .fromAccount("202206101986")
                .toAccount("202223111987")
                .amount(500.5).build();
    }

    private Transaction aTransaction() {
        return Transaction.builder()
                .id(1l)
                .fromAccount("202223111987")
                .toAccount("202206101986")
                .amount(49.75)
                .timestamp(new Timestamp(System.currentTimeMillis())).build();
    }

    private Account anAccount() {
        return Account.builder()
                .id(1l)
                .name("Srushti")
                .number("202206101986")
                .balance(340.50).build();
    }
}

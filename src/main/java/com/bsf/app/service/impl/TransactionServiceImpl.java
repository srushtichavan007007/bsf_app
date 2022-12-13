package com.bsf.app.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsf.app.dto.TransactionRequest;
import com.bsf.app.exception.InsufficientFundException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Account;
import com.bsf.app.model.Transaction;
import com.bsf.app.repository.AccountRepository;
import com.bsf.app.repository.TransactionRepository;
import com.bsf.app.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional
	public Transaction transfer(TransactionRequest transactionRequest) {

		logger.info("Transfer started with transaction: " + transactionRequest);

		/* Account Validation */
		Account fromAccount = accountRepository.findByNumber(transactionRequest.getFromAccount())
				.orElseThrow(() -> new InvalidAccountException());
		Account toAccount = accountRepository.findByNumber(transactionRequest.getToAccount())
				.orElseThrow(() -> new InvalidAccountException());

		/* Amount Validation */
		if (transactionRequest.getAmount() > fromAccount.getBalance()) {
			throw new InsufficientFundException();
		}

		/* Update Account Balance */
		fromAccount.setBalance(fromAccount.getBalance() - transactionRequest.getAmount());
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + transactionRequest.getAmount());
		accountRepository.save(toAccount);

		/* Execute Transaction */
		Transaction transaction = new Transaction();
		transaction.setFromAccount(transactionRequest.getFromAccount());
		transaction.setToAccount(transactionRequest.getToAccount());
		transaction.setAmount(transactionRequest.getAmount());
		transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
		Transaction txn = transactionRepository.save(transaction);

		logger.info("Transfer completed.");

		return txn;
	}

}

package com.bsf.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsf.app.dto.AccountRequest;
import com.bsf.app.exception.DuplicateAccountException;
import com.bsf.app.exception.InvalidAccountException;
import com.bsf.app.model.Account;
import com.bsf.app.repository.AccountRepository;
import com.bsf.app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account save(AccountRequest accountRequest) {
		/* Check for duplicate account */
		accountRepository.findByNumber(accountRequest.getNumber())
			.ifPresent(acc -> { throw new DuplicateAccountException(); });
		Account account = new Account();
		account.setNumber(accountRequest.getNumber());
		account.setName(accountRequest.getName());
		account.setBalance(accountRequest.getBalance());
		return accountRepository.save(account);
	}

	@Override
	public Account find(String number) {
		Account account = accountRepository.findByNumber(number)
				.orElseThrow(() -> new InvalidAccountException());
		return account;
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}

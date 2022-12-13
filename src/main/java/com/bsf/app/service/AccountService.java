package com.bsf.app.service;

import java.util.List;

import com.bsf.app.dto.AccountRequest;
import com.bsf.app.model.Account;

public interface AccountService {

	Account save(AccountRequest accountRequest);

	Account find(String number);

	List<Account> findAll();

}
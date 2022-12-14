package com.bsf.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bsf.app.controller.AccountController;
import com.bsf.app.controller.TransactionController;
import com.bsf.app.service.AccountService;
import com.bsf.app.service.TransactionService;
import com.bsf.app.service.impl.AccountServiceImpl;
import com.bsf.app.service.impl.TransactionServiceImpl;

@SpringBootTest
class BsfApplicationTests {

	@Autowired
	private AccountController accountController;

	@Autowired
	private TransactionController transactionController;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Test
	void contextLoads() {
		assertThat(accountController).isNotNull();
		assertThat(transactionController).isNotNull();
		assertThat(accountService).isNotNull().isInstanceOf(AccountServiceImpl.class);
		assertThat(transactionService).isNotNull().isInstanceOf(TransactionServiceImpl.class);
	}

}

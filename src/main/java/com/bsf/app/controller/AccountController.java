package com.bsf.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsf.app.dto.AccountRequest;
import com.bsf.app.model.Account;
import com.bsf.app.service.AccountService;

@RestController
@RequestMapping("api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PutMapping("/create")
	public ResponseEntity<?> createAccount(@RequestBody AccountRequest accountRequest) {
		Account acc = accountService.save(accountRequest);
		return new ResponseEntity<>(acc, HttpStatus.CREATED);
	}

	@GetMapping("/get/{number}")
	public ResponseEntity<?> getAccount(@PathVariable String number) {
		Account account = accountService.find(number);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		List<Account> accounts = accountService.findAll();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

}

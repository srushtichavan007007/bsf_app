package com.bsf.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsf.app.dto.TransactionRequest;
import com.bsf.app.model.Transaction;
import com.bsf.app.service.TransactionService;

@RestController
@RequestMapping("api")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PutMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestBody TransactionRequest transactionRequest) {
		Transaction txn = transactionService.transfer(transactionRequest);
		return new ResponseEntity<>(txn, HttpStatus.ACCEPTED);
	}

}

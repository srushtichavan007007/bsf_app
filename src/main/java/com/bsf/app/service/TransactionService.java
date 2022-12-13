package com.bsf.app.service;

import com.bsf.app.dto.TransactionRequest;
import com.bsf.app.model.Transaction;

public interface TransactionService {

	Transaction transfer(TransactionRequest transactionRequest);

}

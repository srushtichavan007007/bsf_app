package com.bsf.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {

	private String fromAccount;
	private String toAccount;
	private Double amount;
}

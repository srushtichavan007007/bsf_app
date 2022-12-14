package com.bsf.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {

	private String number;
	private String name;
	private Double balance;
}

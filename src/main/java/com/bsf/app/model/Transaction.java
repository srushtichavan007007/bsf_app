package com.bsf.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

	@Id 
	@GeneratedValue
	private Long id;
	private String fromAccount;
	private String toAccount;
	private Double amount;
	private Timestamp timestamp;
}

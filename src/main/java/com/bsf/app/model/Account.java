package com.bsf.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id 
	@GeneratedValue
	private Long id;
	private String number;
	private String name;
	private Double balance;
}

package com.bsf.app.dto;

public class AccountRequest {

	private String number;
	private String name;
	private Double balance;

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AccountRequest [number=" + number + ", name=" + name + ", balance=" + balance + "]";
	}

}

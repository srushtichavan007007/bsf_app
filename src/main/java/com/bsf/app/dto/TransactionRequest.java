package com.bsf.app.dto;

public class TransactionRequest {

	private String fromAccount;
	private String toAccount;
	private Double amount;

	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TransactionRequest [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount
				+ "]";
	}

}

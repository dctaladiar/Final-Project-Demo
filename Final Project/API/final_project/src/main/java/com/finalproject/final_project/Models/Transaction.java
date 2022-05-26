package com.finalproject.final_project.Models;

import java.util.Date;

public class Transaction {
	
	private int id;

	private Date modifiedDate;

	private String transactionType;

	private String typeOfAccount;

	private double amount;
	
	private int accountId;
	
	// Constructors
	
	public Transaction( ) {
		
	}
	
	public Transaction(String transactionType, String typeOfAccount, double amount, int accountId) {
		super();
		this.transactionType = transactionType;
		this.typeOfAccount = typeOfAccount;
		this.amount = amount;
		this.accountId = accountId;
	}
	
	// Getters and setters
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTypeOfAccount() {
		return typeOfAccount;
	}
	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	
	
	
}

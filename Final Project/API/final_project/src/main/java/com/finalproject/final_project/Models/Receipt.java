package com.finalproject.final_project.Models;

import java.util.Date;

public class Receipt {
	private int id;
	private Date modifiedDate;
	private String transactionType;
	private String typeOfAccount;
	private double amount;
	private double balance;
	
	
	
	public Receipt() {
		super();
	}
	
	
	public Receipt(int id, Date modifiedDate, String transactionType, String typeOfAccount, double amount,
			double balance) {
		super();
		this.id = id;
		this.modifiedDate = modifiedDate;
		this.transactionType = transactionType;
		this.typeOfAccount = typeOfAccount;
		this.amount = amount;
		this.balance = balance;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}

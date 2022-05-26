package com.finalproject.final_project.Models;

public class Card {
	
	private int id;
	

	private String pinCode;

	private int accountId;

	
	// Constructor
		
	

	public Card() {
		super();
	}
	
	public Card(String pinCode) {
		super();
		this.pinCode = pinCode;
	}
	
	public Card(String pinCode, int accountId) {
		super();
		this.pinCode = pinCode;
		this.accountId = accountId;
	}

	// Getters and setters
	
	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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

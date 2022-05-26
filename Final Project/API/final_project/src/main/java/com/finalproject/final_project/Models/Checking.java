package com.finalproject.final_project.Models;

public class Checking {
	

	private int id;

	private double balance;
	
	private int accountId;

	// Constructor
		
	public Checking() {
		super();
	}
	public Checking(double balance) {
		super();
		this.balance = balance;
	}
	
	public Checking(double balance, int accountId) {
		super();
		this.balance = balance;
		this.accountId = accountId;
	}


	// Getters and setters
	
	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
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

	public int overrideWithdraw(double amount) {

		 if(amount <= this.getBalance() &&  (this.getBalance() - amount >= 0)) {
			this.balance -= amount;
			return 1;
		} else {
			return 0;
		}
	}
	
	public int withdraw(double amount, double totalAmountWithdrewOnADay) {
		
		if((totalAmountWithdrewOnADay + amount) > 50000 ) {
			return 2;
		} else if(amount <= this.getBalance() && (this.getBalance() - amount >= 2000.00)) {
			this.balance -= amount;
			return 1;
		} else {
			return 0;
		}
		
	}


	public double deposit(double amount) {
		if(amount >= 300) {
			if(amount % 300 == 0) {
				this.balance += amount;
				return 0;
			} else {
				double remainder = amount % 300;
				double amountToBeProcessed = amount - remainder;
				this.balance += amountToBeProcessed;
				return remainder;
			}
		} else {
			return amount;
		}
		
	}
	
	public int transferToAnotherAccount(Checking anotherAccount, double amount, double totalAmountWithdrewOnADay) {
		if((totalAmountWithdrewOnADay + amount) > 50000 ) {
			return 2;
		} else if(this.getBalance() > amount && (this.getBalance() - amount >= 5000.00)) {
			this.setBalance(this.getBalance() - amount); 
			anotherAccount.setBalance(anotherAccount.getBalance() + amount);
			return 1;
		} else {
			return 0;
		}
	}
	
	public int overrideTransferOfFundsToOtherAccount(Checking anotherAccount, double amount) {

		 if(amount <= this.getBalance() &&  (this.getBalance() - amount >= 0)) {
			this.setBalance(this.getBalance() - amount); 
			anotherAccount.setBalance(anotherAccount.getBalance() + amount);
			return 1;
		} else {
			return 0;
		}
	}
	

	
}

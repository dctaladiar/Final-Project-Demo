package com.finalproject.final_project.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
	
	private int id;

	private Date modifiedDate;

	private String transactionType;

	private String typeOfAccount;

	private double amount;
	
	private int accountId;
	
}

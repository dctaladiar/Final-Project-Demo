package com.finalproject.final_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.Receipt;
import com.finalproject.final_project.Models.Transaction;
import com.finalproject.final_project.Repository.TransactionRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/transaction")
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepo;
	
	
	@PostMapping(value = "/insertNewTransaction")
	public boolean insertNewTransaction(@RequestBody Transaction transaction) {
		return transactionRepo.insertNewTransaction(transaction);
	}
	
	@PostMapping(value = "/getAllTransactionsForEachAccountById/{id}")
	public List<Transaction> getAllTransactionsForEachAccountById(@PathVariable(name = "id") int id) {
		return transactionRepo.getAllTransactionsForEachAccountById(id);
	}
	
	@PostMapping(value = "/getTransactionForReceipt/{id}/{id_2}")
	public List<Receipt> getTransactionForReceipt(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2) {
		return transactionRepo.getTransactionForReceipt(id, id_2);
	}
}

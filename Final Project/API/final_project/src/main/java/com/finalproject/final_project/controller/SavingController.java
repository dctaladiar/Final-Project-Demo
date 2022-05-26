package com.finalproject.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.Saving;
import com.finalproject.final_project.Repository.SavingRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/saving")
public class SavingController {

	@Autowired
	SavingRepository savingRepo;
	

	
	@PostMapping(value = "/getSavingsBalanceById/{id}")
	public double getSavingsBalanceById(@PathVariable(name = "id") int id){
		return savingRepo.getSavingsBalanceById(id);

	}
	
	@PutMapping(value = "/withdrawInSavingBalance/{id}")
	public double withdrawInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.withdrawInSavingBalance(id, saving);
	}
	
	@PutMapping(value = "/overrideWithdrawInSavingBalance/{id}")
	public double overrideWithdrawInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.overrideWithdrawInSavingBalance(id, saving);
	}
	
	@PutMapping(value = "/depositInSavingBalance/{id}")
	public double depositInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.depositInSavingBalance(id, saving);
	}
	
	
	@PutMapping(value = "/transferFundsToOtherAccount/{id}/{id_2}")
	public int transferToOtherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Saving saving) {
		return savingRepo.transferFundsToOtherAccount(id, id_2, saving);
	}
	
	@PutMapping(value = "/overrideTransferOfFundsToOtherAccount/{id}/{id_2}")
	public int overrideTransferOfFundsToAnotherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Saving saving) {
		return savingRepo.overrideTransferOfFundsToOtherAccount(id, id_2, saving);
	}
	
	

	
}

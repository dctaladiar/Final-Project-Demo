package com.finalproject.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.Current;
import com.finalproject.final_project.Repository.CurrentRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/current")
public class CurrentController {

	@Autowired
	CurrentRepository currentRepo;
	

	
	@PostMapping(value = "/getCurrentBalanceById/{id}")
	public double getCurrentBalanceById(@PathVariable(name = "id") int id){
		return currentRepo.getCurrentBalanceById(id);

	}
	
	@PutMapping(value = "/withdrawInCurrentBalance/{id}")
	public double withdrawInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.withdrawInCurrentBalance(id, current);
	}
	
	@PutMapping(value = "/overrideWithdrawInCurrentBalance/{id}")
	public double overrideWithdrawInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.overrideWithdrawInCurrentBalance(id, current);
	}
	
	@PutMapping(value = "/depositInCurrentBalance/{id}")
	public double depositInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.depositInCurrentBalance(id, current);
	}
	
	
	@PutMapping(value = "/transferFundsToOtherAccount/{id}/{id_2}")
	public int transferToOtherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Current current) {
		return currentRepo.transferFundsToOtherAccount(id, id_2, current);
	}
	
	@PutMapping(value = "/overrideTransferOfFundsToOtherAccount/{id}/{id_2}")
	public int overrideTransferOfFundsToAnotherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Current current) {
		return currentRepo.overrideTransferOfFundsToOtherAccount(id, id_2, current);
	}
	
	

	
}

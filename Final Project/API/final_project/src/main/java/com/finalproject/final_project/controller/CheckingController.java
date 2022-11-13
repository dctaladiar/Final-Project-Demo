package com.finalproject.final_project.controller;

import com.finalproject.final_project.Enum.TypeOfAccountEnumData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.Checking;
import com.finalproject.final_project.Repository.CheckingRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/checking")
public class CheckingController {

	@Autowired
	CheckingRepository checkingRepo;
	TypeOfAccountEnumData typeOfAccountEnumData;
	

	
	@PostMapping(value = "/getCheckingBalanceById/{id}")
	public double getCheckingBalanceById(@PathVariable(name = "id") int id){
		return checkingRepo.getBalanceById(id, typeOfAccountEnumData.CHECKING);

	}
	
	@PutMapping(value = "/withdrawInCheckingBalance/{id}")
	public double withdrawInCheckingBalance(@PathVariable(name = "id") int id, @RequestBody Checking checking) {
		return checkingRepo.withdraw(id, checking, typeOfAccountEnumData.CHECKING);
	}
	
	@PutMapping(value = "/overrideWithdrawInCheckingBalance/{id}")
	public double overrideWithdrawInCheckingBalance(@PathVariable(name = "id") int id, @RequestBody Checking checking) {
		return checkingRepo.overrideWithdraw(id, checking, typeOfAccountEnumData.CHECKING);
	}
	
	@PutMapping(value = "/depositInCheckingBalance/{id}")
	public double depositInCheckingBalance(@PathVariable(name = "id") int id, @RequestBody Checking checking) {
		return checkingRepo.deposit(id, checking, typeOfAccountEnumData.CHECKING);
	}
	
	
	@PutMapping(value = "/transferFundsToOtherAccount/{id}/{id_2}")
	public int transferToOtherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Checking checking) {
		return checkingRepo.transferFundsToOtherAccount(id, id_2, checking, typeOfAccountEnumData.CHECKING);
	}
	
	@PutMapping(value = "/overrideTransferOfFundsToOtherAccount/{id}/{id_2}")
	public int overrideTransferOfFundsToAnotherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Checking checking) {
		return checkingRepo.overrideTransferOfFundsToOtherAccount(id, id_2, checking, typeOfAccountEnumData.CHECKING);
	}
	
	

	
}

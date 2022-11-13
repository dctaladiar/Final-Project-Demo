package com.finalproject.final_project.controller;

import com.finalproject.final_project.Enum.TypeOfAccountEnumData;
import com.finalproject.final_project.Models.Current;
import com.finalproject.final_project.Repository.CurrentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/current")
public class CurrentController {

	@Autowired
	CurrentRepository currentRepo;
	TypeOfAccountEnumData typeOfAccountEnumData;
	

	
	@PostMapping(value = "/getCurrentBalanceById/{id}")
	public double getCurrentBalanceById(@PathVariable(name = "id") int id){
		return currentRepo.getBalanceById(id, typeOfAccountEnumData.CURRENT);

	}
	
	@PutMapping(value = "/withdrawInCurrentBalance/{id}")
	public double withdrawInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.withdraw(id, current, typeOfAccountEnumData.CURRENT);
	}
	
	@PutMapping(value = "/overrideWithdrawInCurrentBalance/{id}")
	public double overrideWithdrawInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.overrideWithdraw(id, current, typeOfAccountEnumData.CURRENT);
	}
	
	@PutMapping(value = "/depositInCurrentBalance/{id}")
	public double depositInCurrentBalance(@PathVariable(name = "id") int id, @RequestBody Current current) {
		return currentRepo.deposit(id, current, typeOfAccountEnumData.CURRENT);
	}
	
	
	@PutMapping(value = "/transferFundsToOtherAccount/{id}/{id_2}")
	public int transferToOtherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Current current) {
		return currentRepo.transferFundsToOtherAccount(id, id_2, current, typeOfAccountEnumData.CURRENT);
	}
	
	@PutMapping(value = "/overrideTransferOfFundsToOtherAccount/{id}/{id_2}")
	public int overrideTransferOfFundsToAnotherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Current current) {
		return currentRepo.overrideTransferOfFundsToOtherAccount(id, id_2, current, typeOfAccountEnumData.CURRENT);
	}
	
	

	
}

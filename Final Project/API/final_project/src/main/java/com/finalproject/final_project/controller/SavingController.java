package com.finalproject.final_project.controller;

import com.finalproject.final_project.Enum.TypeOfAccountEnumData;
import com.finalproject.final_project.Models.Saving;
import com.finalproject.final_project.Repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/saving")
public class SavingController {

	@Autowired
	SavingRepository savingRepo;
	TypeOfAccountEnumData typeOfAccountEnumData;
	

	
	@PostMapping(value = "/getSavingsBalanceById/{id}")
	public double getSavingsBalanceById(@PathVariable(name = "id") int id){
		return savingRepo.getBalanceById(id, typeOfAccountEnumData.SAVINGS);

	}
	
	@PutMapping(value = "/withdrawInSavingBalance/{id}")
	public double withdrawInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.withdraw(id, saving, typeOfAccountEnumData.SAVINGS);
	}
	
	@PutMapping(value = "/overrideWithdrawInSavingBalance/{id}")
	public double overrideWithdrawInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.overrideWithdraw(id, saving, typeOfAccountEnumData.SAVINGS);
	}
	
	@PutMapping(value = "/depositInSavingBalance/{id}")
	public double depositInSavingBalance(@PathVariable(name = "id") int id, @RequestBody Saving saving) {
		return savingRepo.deposit(id, saving, typeOfAccountEnumData.SAVINGS);
	}
	
	
	@PutMapping(value = "/transferFundsToOtherAccount/{id}/{id_2}")
	public int transferToOtherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Saving saving) {
		return savingRepo.transferFundsToOtherAccount(id, id_2, saving, typeOfAccountEnumData.SAVINGS);
	}
	
	@PutMapping(value = "/overrideTransferOfFundsToOtherAccount/{id}/{id_2}")
	public int overrideTransferOfFundsToAnotherAccount(@PathVariable(name = "id") int id, @PathVariable(name = "id_2") int id_2 , @RequestBody Saving saving) {
		return savingRepo.overrideTransferOfFundsToOtherAccount(id, id_2, saving, typeOfAccountEnumData.SAVINGS);
	}
	
	

	
}

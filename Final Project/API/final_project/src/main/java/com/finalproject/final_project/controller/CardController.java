package com.finalproject.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.Card;
import com.finalproject.final_project.Repository.CardRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/card")
public class CardController {

	@Autowired
	CardRepository cardRepo;
	

	@PostMapping(value = "/checkCardById/{id}")
	public int checkCardById(@PathVariable(name = "id") int id){
		return cardRepo.checkCardById(id);
	}
	
	@PostMapping(value = "/checkIfPinCodeIsCorrect/{id}")
	public boolean checkIfPinCodeIsCorrect(@PathVariable(name = "id") int id, @RequestBody Card card){
		return cardRepo.checkIfPinCodeIsCorrect(id, card);
		
	}
	
	@PostMapping(value = "/checkIfPinCodeIsCorrectForChangePinCode/{id}")
	public boolean checkIfPinCodeIsCorrectForChangePinCode(@PathVariable(name = "id") int id, @RequestBody Card card){
		return cardRepo.checkIfPinCodeIsCorrectForChangePinCode(id, card);
		
	}
	
	@PutMapping(value = "/changePinCode/{id}")
	public String changePinCode(@PathVariable(name = "id") int id, @RequestBody Card card) {
		return cardRepo.changePinCode(id, card);
	}
	
}

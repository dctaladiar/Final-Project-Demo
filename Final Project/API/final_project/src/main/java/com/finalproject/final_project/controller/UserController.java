package com.finalproject.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.final_project.Models.User;
import com.finalproject.final_project.Repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping(value = "/account")
public class UserController {

	@Autowired
	UserRepository accountRepo;
	
	@PostMapping(value = "/getAccountById/{id}")
	public User getAccountById(@PathVariable(name = "id") int id) throws NotFoundException {
		return accountRepo.getAccountById(id);
	}
	
	
}

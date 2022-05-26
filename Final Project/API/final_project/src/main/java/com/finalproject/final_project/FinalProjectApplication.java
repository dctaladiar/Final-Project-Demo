package com.finalproject.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.finalproject.final_project.Repository.SavingRepository;

@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner{

	
	@Autowired 
	SavingRepository savingRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
			
	
	}

}

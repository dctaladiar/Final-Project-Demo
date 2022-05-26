package com.finalproject.final_project.Models;

public class Account {
	
	private int id;
	private String name;
	
	// Constructor
	public Account() {
		super();
	}


	public Account(String name) {
		super();
		this.name = name;
	}

	// Getters and setters


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}



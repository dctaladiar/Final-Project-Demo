package com.finalproject.final_project.Models;


import com.finalproject.final_project.Implementation.BusinessLogicImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account extends BusinessLogicImpl {
    private int id;

    private double balance;

    private int accountId;
}

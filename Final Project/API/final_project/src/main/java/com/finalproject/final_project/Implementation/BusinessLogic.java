package com.finalproject.final_project.Implementation;

import com.finalproject.final_project.Models.Account;

public interface BusinessLogic {
    public int overrideWithdraw(double amount, Account account);
    public int withdraw(double amount, double totalAmountWithdrewOnADay, Account account);
    public double deposit(double amount, Account account);
    public int transferToAnotherAccount(Account anotherAccount, double amount, double totalAmountWithdrewOnADay, Account account);
    public int overrideTransferOfFundsToOtherAccount(Account anotherAccount, double amount, Account account);

}

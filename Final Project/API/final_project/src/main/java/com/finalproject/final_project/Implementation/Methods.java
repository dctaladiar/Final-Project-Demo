package com.finalproject.final_project.Implementation;

import com.finalproject.final_project.Models.Account;

public interface Methods {
    public double getBalanceById(int id, String accountType);
    public double withdraw(int id, Account account, String accountType);
    public double overrideWithdraw(int id, Account account, String accountType);
    public double deposit(int id, Account account, String accountType);
    public int transferFundsToOtherAccount (int id, int id2, Account account, String accountType);
    public int overrideTransferOfFundsToOtherAccount(int id, int id2, Account account, String accountType);
}

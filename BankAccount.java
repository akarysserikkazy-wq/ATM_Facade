package com.company;

public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean hasEnoughFunds(int amount) {
        return balance >= amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
        System.out.println("Withdrawn from account: " + amount + " KZT");
    }
}
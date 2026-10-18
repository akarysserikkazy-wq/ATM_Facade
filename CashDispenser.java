package com.company;

public class CashDispenser {
    private int cashInside;

    public CashDispenser(int cashInside) {
        this.cashInside = cashInside;
    }

    public boolean canDispense(int amount) {
        return amount > 0 && amount % 10 == 0 && amount <= cashInside;
    }

    public void dispense(int amount) {
        cashInside -= amount;
        System.out.println("Dispensing cash: " + amount);
    }
}
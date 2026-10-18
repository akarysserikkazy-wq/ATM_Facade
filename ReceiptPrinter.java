package com.company;

public class ReceiptPrinter {
    public void printReceipt(int amount, int balanceAfter) {
        System.out.println("----- RECEIPT -----");
        System.out.println("Withdrawn: " + amount + " KZT");
        System.out.println("Balance: " + balanceAfter + " KZT");
        System.out.println("-------------------");
    }
}
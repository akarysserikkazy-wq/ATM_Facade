package com.company;

public class Main {
    public static void main(String[] args) {
        AtmFacade atm = new AtmFacade();

        System.out.println("--- 1. Success ---");
        atm.withdrawCash("1234", 10000);

        System.out.println("\n--- 2. Wrong PIN ---");
        atm.withdrawCash("0000", 5000);

        System.out.println("\n--- 3. Insufficient funds ---");
        atm.withdrawCash("1234", 60000);

        System.out.println("\n--- 4. Invalid amount ---");
        atm.withdrawCash("1234", 125);

        System.out.println("\n--- 5. Card blocked ---");
        atm.withdrawCash("1", 1000);
        System.out.println();
        atm.withdrawCash("2", 1000);
        System.out.println();
        atm.withdrawCash("3", 1000);
        System.out.println();
        atm.withdrawCash("1234", 1000);
    }
}
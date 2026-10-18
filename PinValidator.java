package com.company;

public class PinValidator {
    private static final int MAX_ATTEMPTS = 3;
    private final String correctPin;
    private int failedAttempts = 0;

    public PinValidator(String correctPin) {
        this.correctPin = correctPin;
    }

    public boolean validate(String pin) {
        if (failedAttempts >= MAX_ATTEMPTS) {
            System.out.println("Card is blocked.");
            return false;
        }
        if (!correctPin.equals(pin)) {
            failedAttempts++;
            System.out.println("Wrong PIN. Attempts left: " + (MAX_ATTEMPTS - failedAttempts));
            return false;
        }
        failedAttempts = 0;
        System.out.println("PIN accepted.");
        return true;
    }
}
package com.company;

public class AtmFacade {
    private final CardReader cardReader;
    private final PinValidator pinValidator;
    private final BankAccount account;
    private final CashDispenser cashDispenser;
    private final ReceiptPrinter receiptPrinter;

    public AtmFacade() {
        this(new CardReader(), new PinValidator("1234"), new BankAccount(50000),
                new CashDispenser(100000), new ReceiptPrinter());
    }

    public AtmFacade(CardReader cardReader,
                     PinValidator pinValidator,
                     BankAccount account,
                     CashDispenser cashDispenser,
                     ReceiptPrinter receiptPrinter) {
        this.cardReader = cardReader;
        this.pinValidator = pinValidator;
        this.account = account;
        this.cashDispenser = cashDispenser;
        this.receiptPrinter = receiptPrinter;
    }

    public void withdrawCash(String pin, int amount) {
        System.out.println("Withdrawing " + amount + " KZT from ATM...");
        cardReader.insertCard();

        if (!pinValidator.validate(pin)) {
            cancel("PIN check failed");
            return;
        }
        if (!cashDispenser.canDispense(amount)) {
            cancel("ATM cannot dispense this amount");
            return;
        }
        if (!account.hasEnoughFunds(amount)) {
            cancel("Insufficient funds");
            return;
        }

        account.withdraw(amount);
        cashDispenser.dispense(amount);
        receiptPrinter.printReceipt(amount, account.getBalance());
        cardReader.ejectCard();
        System.out.println("Operation completed.");
    }

    private void cancel(String reason) {
        System.out.println("Operation cancelled: " + reason);
        cardReader.ejectCard();
    }
}
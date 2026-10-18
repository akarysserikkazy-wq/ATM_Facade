## Assignment #4 — Facade pattern
### Student: Serikkazy Akarys
### Group: SE-2514

### About the product.

The application simulates an ATM. A client wants to do one simple thing, withdraw cash, but the operation involves several subsystem classes that must be called in the correct order: reading the card, validating the PIN, checking the account balance, dispensing cash and printing a receipt. The `AtmFacade` hides this coordination behind a single method, `withdrawCash(pin, amount)`.

All amounts are in KZT. The default ATM is assembled by the no-argument constructor of `AtmFacade`, so the client never touches the subsystem classes.

### How the Facade works

`withdrawCash(pin, amount)` performs these steps in order: insert the card, validate the PIN, check that the ATM can dispense the amount, check that the account has enough funds, withdraw from the account, dispense cash, print the receipt, eject the card. If any check fails, the operation is cancelled and the card is ejected.

The subsystem classes do not know that the facade exists and hold no references to it.


### Clean Code Requirements
#### 1.	Meaningful names that make the Facade's simplified API self-explanatory1ю

    atm.withdrawCash(pin, amount);

    account.hasEnoughFunds(amount);

    cashDispenser.canDispense(amount);

    pinValidator.validate(pin);

- The Facade's whole API is one self-explanatory method, so the client needs no knowledge of PINs, accounts or dispensers.
- Boolean checks read as questions (`hasEnoughFunds`, `canDispense`), actions are verbs (`insertCard`, `ejectCard`, `dispense`, `printReceipt`).


#### 2.	Single Responsibility: the Facade coordinates, it does not reimplement subsystem logic

 public boolean validate(String pin) { ... }      // PinValidator: owns PIN logic and the failed-attempt counter

    public boolean hasEnoughFunds(int amount) { ... } // BankAccount: owns the balance

    public boolean canDispense(int amount) { ... }    // CashDispenser: owns the cash inside the ATM

- `AtmFacade` only decides the order of steps and when to stop. It never compares PINs, calculates balances or counts banknotes itself.
- If the PIN rules change (for example, 5 attempts instead of 3), only `PinValidator` is modified.

#### 3.	No leaking of subsystem details through the Facade's public interface

 public void withdrawCash(String pin, int amount) { ... }

    public AtmFacade() { ... }   // assembles a default ATM, the client creates no subsystem objects

- The operation takes only primitive types and returns nothing: no subsystem class, internal state or subsystem-specific result is exposed.
- The second constructor accepts subsystem objects only so they can be injected (composition, as in the lecture's `HomeTheaterFacade`). The operation itself stays independent of their details.

#### 4.	Small, focused subsystem classes with clear responsibilities

private static final int MAX_ATTEMPTS = 3;

    public class CardReader { insertCard(); ejectCard(); }

    public class ReceiptPrinter { printReceipt(amount, balanceAfter); }

- Every subsystem class does one thing and has 2 to 4 short methods.
- `PinValidator` keeps its limit in the named constant `MAX_ATTEMPTS`.


#### 5.	Proper error handling for subsystem coordination failures

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
    private void cancel(String reason) {
        System.out.println("Operation cancelled: " + reason);
        cardReader.ejectCard();
    }

- All checks (PIN, ATM cash, balance) happen before any irreversible action, so an account is never debited without cash being dispensed.
- Every failure goes through one method, `cancel(reason)`: it reports a specific reason and always ejects the card, and the cancellation logic is not duplicated.

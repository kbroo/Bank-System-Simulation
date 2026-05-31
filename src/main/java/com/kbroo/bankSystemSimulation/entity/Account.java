package com.kbroo.bankSystemSimulation.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kbroo.bankSystemSimulation.exception.InsufficientAccountException;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DebitAccount.class, name = "DEBIT"),
        @JsonSubTypes.Type(value = CreditAccount.class, name = "CREDIT"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SAVINGS")
})

abstract public class Account {
    protected String accountNumber;
    protected Client owner;
    protected BigDecimal balance;
    protected AccountType accountType;
    protected LocalDate openedIn;

    public Account() {}

    public Account(String accountNumber, Client owner, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = BigDecimal.valueOf(0);
        this.accountType = accountType;
        this.openedIn = LocalDate.now();
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
    public Client getOwner() {
        return this.owner;
    }
    public BigDecimal getBalance() {
        return this.balance;
    }
    public AccountType getAccountType() {
        return this.accountType;
    }
    public LocalDate getOpenedIn() {
        return this.openedIn;
    }

    public abstract BigDecimal calculateMonthlyFee();

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Отрицательная или равная нулю сумма пополнения.");
        } else {
            this.balance = this.balance.add(amount);
        }
    };

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Отрицательная или равная нулю сумма вывода средств.");
            return;
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientAccountException("Недостаточно средств для вывода.");
        }
        this.balance = this.balance.subtract(amount);
        System.out.println("Успешно снято: " + amount + "$");
    };
}

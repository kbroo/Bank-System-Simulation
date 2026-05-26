package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

abstract public class Account {
    protected final String accountNumber;
    protected final Client owner;
    protected BigDecimal balance;
    protected final AccountType accountType;
    protected final LocalDate openedAt;

    public Account(String accountNumber, Client owner, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = BigDecimal.valueOf(0);
        this.accountType = accountType;
        this.openedAt = LocalDate.now();
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
        return this.openedAt;
    }

//    public void setBalance(BigDecimal balance) {
//        this.balace
//    }

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
            System.out.println("Недостаточно средств для вывода.");
            return;
        }
        this.balance = this.balance.subtract(amount);
        System.out.println("Успешно снято: " + amount + "$");
    };
}

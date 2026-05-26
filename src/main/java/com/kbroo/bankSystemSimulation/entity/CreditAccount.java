package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CreditAccount extends Account {
    private final BigDecimal interestRate;
    private BigDecimal creditLimit;

    public CreditAccount(String accountNumber, Client owner) {
        super(accountNumber, owner, AccountType.CREDIT);
        this.interestRate = this.accountType.getInterestRate();
        this.creditLimit = BigDecimal.valueOf(this.getOwner().getCreditRating() * 10000L);
    }

    public BigDecimal getInterestRate() {
        return this.interestRate;
    }

    public BigDecimal getCreditLimit() {
        return this.creditLimit;
    }

    @Override
    public BigDecimal calculateMonthlyFee() {
        BigDecimal fee = BigDecimal.valueOf(0);
        if (this.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            fee = fee.multiply(interestRate);
            fee = fee.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        }
        return fee;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Отрицательная или равная нулю сумма для снятия средств.");
            return;
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        BigDecimal minAllowed = creditLimit.negate();
        if (newBalance.compareTo(minAllowed) < 0) {
            System.out.println("Превышен кредитный лимит по снятию средств.");
            return;
        }
        this.balance = newBalance;
        System.out.println("С баланса снято: " + amount + "$");
    }
}

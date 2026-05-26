package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;

public class DebitAccount extends Account {
    private final BigDecimal interestRate;

    public DebitAccount(String accountNumber, Client owner) {
        super(accountNumber, owner, AccountType.DEBIT);
        this.interestRate = this.getAccountType().getInterestRate();
    }

    public BigDecimal getInterestRate() {
        return this.interestRate;
    }

    @Override
    public BigDecimal calculateMonthlyFee() {
        return BigDecimal.valueOf(0);
    }
}

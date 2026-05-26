package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;

public enum AccountType {
    DEBIT(BigDecimal.valueOf(0)),
    CREDIT(BigDecimal.valueOf(0.15)),
    SAVINGS(BigDecimal.valueOf(0.05));

    private final BigDecimal interestRate;

    AccountType(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return this.interestRate;
    }
}

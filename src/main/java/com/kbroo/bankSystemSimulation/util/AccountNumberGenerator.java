package com.kbroo.bankSystemSimulation.util;

import com.kbroo.bankSystemSimulation.entity.AccountType;

public class AccountNumberGenerator {
    private int counter;

    public AccountNumberGenerator() {
        this.counter = 0;
    }

    public AccountNumberGenerator(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return this.counter;
    }

    public String generate(AccountType type) {
        counter++;
        String prefix = switch (type) {
            case DEBIT -> "DBT";
            case CREDIT -> "CRT";
            case SAVINGS -> "SVG";
        };
        return String.format("%s-%06d", prefix, counter);
    }
}

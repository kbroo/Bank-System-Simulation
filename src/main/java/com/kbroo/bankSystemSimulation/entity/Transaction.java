package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final Account from;
    private final Account to;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String description;

    public Transaction(Account from, Account to, BigDecimal amount, String description) {
        this.id = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public UUID getId() {
        return this.id;
    }
    public Account getFrom() {
        return this.from;
    }
    public Account getTo() {
        return this.to;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getDescription() {
        return description;
    }
}

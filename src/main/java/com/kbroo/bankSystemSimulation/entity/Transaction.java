package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;

    public Transaction() {}

    public Transaction(String fromAccount, String toAccount, BigDecimal amount, String description) {
        this.id = UUID.randomUUID();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public UUID getId() {
        return this.id;
    }
    public String getFrom() {
        return this.fromAccount;
    }
    public String getTo() {
        return this.toAccount;
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

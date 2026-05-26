package com.kbroo.bankSystemSimulation.entity;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final String username;
    private final String email;
    private int creditRating;

    public Client(String username, String email) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.creditRating = 5;
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public int getCreditRating() {
        return this.creditRating;
    }

    public void setCreditRating(int creditRating) {
        if (creditRating < 0 || creditRating > 10) {
            System.out.println("Кредитный рейтинг варьируется от 0 до 10");
        } else {
            this.creditRating = creditRating;
        }
    }
}

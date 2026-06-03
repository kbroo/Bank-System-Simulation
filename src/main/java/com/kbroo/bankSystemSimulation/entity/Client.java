package com.kbroo.bankSystemSimulation.entity;

import java.util.UUID;

public class Client {
    private UUID id;
    private String username;
    private String email;
    private int creditRating;

    public Client() {}

    public Client(String username, String email) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.creditRating = 5;
    }

    public UUID getId() {
        return this.id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public void setID(String name) {
    }
}

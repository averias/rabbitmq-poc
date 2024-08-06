package com.example.rabbitmqpoc.model;

import lombok.Getter;

@Getter
public class EmailVerification {
    private String type;
    private String email;

    public EmailVerification() {
        super();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailVerification(String type, String email) {
        this.type = type;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s [type=%s, email=%s]", getClass().getSimpleName(), type, email);
    }
}

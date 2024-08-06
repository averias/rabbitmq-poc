package com.example.rabbitmqpoc.model;

import lombok.Getter;

@Getter
public class AlertMatch {
    private String type;
    private String match;

    public AlertMatch() {
        super();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public AlertMatch(String type, String match) {
        this.type = type;
        this.match = match;
    }

    @Override
    public String toString() {
        return String.format("%s [type=%s, match=%s]", getClass().getSimpleName(), type, match);
    }
}

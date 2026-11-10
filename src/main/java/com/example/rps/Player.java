package com.example.rps;

public class Player {
    private final String name;

    public Player(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Player name must not be blank");
        }
        this.name = name;
    }

    public String name() {
        return name;
    }


}

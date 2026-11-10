package com.example.rps;

public class Player {
    private final String name;

    public Player(String name){
        if (name.isBlank()) {
            throw new IllegalArgumentException("Pet name must not be blank");
        }
    this.name=name;
    }

    public String name(){
        return this.name;
    }


}

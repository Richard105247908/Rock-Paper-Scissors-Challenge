package com.example.rps;

// Make it green with the smallest change you can, then refactor.
public enum Move {
    ROCK, PAPER, SCISSORS;

    public boolean beats(Move other) {
        return this == ROCK && other==SCISSORS ||
        this==PAPER && other ==ROCK ||
        this==SCISSORS && other == PAPER;
    }

}

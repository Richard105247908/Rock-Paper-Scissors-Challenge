package com.example.rps;

// Deliberately incomplete: this compiles, but the first test is RED.
// Make it green with the smallest change you can, then refactor.
public enum Move {
    ROCK, PAPER, SCISSORS;

    public boolean beats(Move other) {
        return false;
    }
}

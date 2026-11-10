package com.example.rps;

// Make it green with the smallest change you can, then refactor.
public enum Move {
    ROCK, PAPER, SCISSORS;

    public boolean beats(Move other) {
        return switch (this) {
            case ROCK     -> other == SCISSORS;
            case PAPER    -> other == ROCK;
            case SCISSORS -> other == PAPER;
        };
    }

}

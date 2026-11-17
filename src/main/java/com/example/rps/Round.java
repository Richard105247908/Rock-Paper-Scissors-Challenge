package com.example.rps;

public class Round {

    private final Move playerMove;
    private final Move computerMove;
    private final Result result;

    public Round(Move playerMove, Move computerMove, Result result) {
        this.playerMove = playerMove;
        this.computerMove = computerMove;
        this.result = result;
    }

    public Move getPlayerMove() {
        return playerMove;
    }

    public Move getComputerMove() {
        return computerMove;
    }

    public Result getResult() {
        return result;
    }
}



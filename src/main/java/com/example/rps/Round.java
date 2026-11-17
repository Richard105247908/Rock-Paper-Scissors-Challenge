package com.example.rps;

public class Round {

    private final Move playerMove;
    private final Move computerMove;
    private final Result result;

    public Round(Move playerMove, Move computerMove, Result result){
        this.playerMove = playerMove;
        this.computerMove = computerMove;
        this.result = result;
    }

    public Move playerMove() {
        return playerMove;
    }

    public Move computerMove(){
        return computerMove;
    }

    public Result result(){
        return result;
    }
}

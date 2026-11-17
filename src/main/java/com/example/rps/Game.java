package com.example.rps;

public class Game {
    private final MoveChooser moveChooser;

    public Game(MoveChooser moveChooser) {
        this.moveChooser = moveChooser;
    }


    public Round play(Move playerMove) {

        Move computerMove = moveChooser.choose();

        Result result;
        if (computerMove == playerMove) {
            result = Result.DRAW;
        } else if (playerMove.beats(computerMove)) {
            result = Result.WIN;
        } else {
            result = Result.LOSE;
        }
        return new Round(playerMove, computerMove, result);
    }
}

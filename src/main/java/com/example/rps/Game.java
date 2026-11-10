package com.example.rps;

public class Game {
    private final MoveChooser moveChooser;

    public Game(MoveChooser moveChooser) {
        this.moveChooser = moveChooser;
    }

    public Result play(Move playerMove) {

        Move computerMove = moveChooser.choose();
        if (computerMove == playerMove) {
            return Result.DRAW;
        }
        if (playerMove.beats(computerMove)) {
            return Result.WIN;
        }
        return Result.LOSE;

    }
}

package com.example.rps;

public class Game {
    private final MoveChooser moveChooser;

    public Game(MoveChooser moveChooser) {
        this.moveChooser=moveChooser;
    }

    public Result play(Move playerMove) {

        return Result.WIN;

    }
}

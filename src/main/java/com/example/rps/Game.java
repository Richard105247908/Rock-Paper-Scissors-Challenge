package com.example.rps;

public class Game {
    private final MoveChooser moveChooser;

    public Game(MoveChooser moveChooser) {
        this.moveChooser = moveChooser;
    }

    public Round play(Move playerMove) {

        Move computerMove = moveChooser.choose();
        Result result = resultOf(playerMove, computerMove);
        return new Round(playerMove, computerMove, result);
    }


    private Result resultOf(Move player, Move computer) {

        if (computer == player) {
            return Result.DRAW;
        }
        if (player.beats(computer)) {
            return Result.WIN;
        }
        return Result.LOSE;
    }
}

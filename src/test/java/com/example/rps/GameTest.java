package com.example.rps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    void playerWinsWhenTheirMoveBeatsTheComputers() {
        Game game = new Game(() -> Move.SCISSORS);

        assertEquals(Result.WIN, game.play(Move.ROCK));
    }

    @Test
    void playerLosesWhenComputerMoveBeatsTheirMove() {
        Game game = new Game(() -> Move.PAPER);

        assertEquals(Result.LOSE, game.play(Move.ROCK));

    }

    @Test
    void samePlayedMoveIsaDraw() {
        Game game = new Game(() -> Move.ROCK);

        assertEquals(Result.DRAW, game.play(Move.ROCK));
    }

    @Test
    void playReturnBothMovesAndTheResult(){
        Game game = new Game(()->Move.SCISSORS);

        Round round =  game.play(Move.ROCK);

        assertEquals(Move.ROCK, round.playerMove());
        assertEquals(Move.SCISSORS, round.computerMove());
        assertEquals(Result.WIN, round.result());

    }
}

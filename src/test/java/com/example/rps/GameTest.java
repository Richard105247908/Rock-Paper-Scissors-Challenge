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
    void playerLoseWhenComputerMoveBeatsTheirMove() {
        Game game = new Game(() -> Move.PAPER);

        assertEquals(Result.LOSE, game.play(Move.ROCK));

    }

    @Test
    void playerDrawWhenComputerMoveBeatsTheirMove() {
        Game game = new Game(() -> Move.ROCK);

        assertEquals(Result.LOSE, game.play(Move.ROCK));
    }
}

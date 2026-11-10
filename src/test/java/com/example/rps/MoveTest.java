package com.example.rps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void rockBeatsScissors() {
        assertTrue(Move.ROCK.beats(Move.SCISSORS));
    }

    @Test
    void rockDoesNotBeatPaper() {assertFalse(Move.ROCK.beats(Move.PAPER)); }

    @Test
    void paperBeatsRock(){ assertTrue(Move.PAPER.beats(Move.ROCK));}

    @Test
    void scissorBeatsPaper(){assertTrue(Move.SCISSORS.beats(Move.PAPER));}

    @ParameterizedTest
    @CsvSource({"ROCK,SCISSORS, true",
    "PAPER,ROCK,true",
    "SCISSORS, PAPER, true"})
    void beatsFollowsTheRulesOfTheGame(Move attacker, Move defender, boolean expected)


}

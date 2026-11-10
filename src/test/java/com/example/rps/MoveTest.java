package com.example.rps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @ParameterizedTest
    @CsvSource({"ROCK,SCISSORS, true",
            "PAPER,ROCK,true",
            "SCISSORS, PAPER, true",
            "SCISSORS,ROCK, false",
            "ROCK,PAPER, false",
            "PAPER,SCISSORS,false",
            "ROCK,ROCK, false",
            "SCISSORS,SCISSORS, false",
            "PAPER,PAPER, false"})
    void beatsFollowsTheRulesOfTheGame(Move attacker, Move defender, boolean expected) {
        assertEquals(expected, attacker.beats(defender));
    }


}

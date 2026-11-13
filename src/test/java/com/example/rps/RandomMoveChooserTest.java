package com.example.rps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomMoveChooserTest {

     private static Random alwaysRolls(int value){
        return new Random() {
            @Override
            public int nextInt(int bound) {
                return value;
            }
        };
     }

     @Test
    void rollOfZeroChoosesRock(){
         MoveChooser chooser =new RandomMoveChooser(alwaysRolls(0));
         assertEquals(Move.ROCK,chooser.choose());
     }

     @ParameterizedTest
    @CsvSource({"0, ROCK",
    "1, PAPER",
    "2, SCISSORS"})
}

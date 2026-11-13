package com.example.rps;

import org.junit.jupiter.api.Test;

import java.util.Random;

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

     }
}

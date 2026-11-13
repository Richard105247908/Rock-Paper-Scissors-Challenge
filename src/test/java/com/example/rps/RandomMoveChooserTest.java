package com.example.rps;

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
}

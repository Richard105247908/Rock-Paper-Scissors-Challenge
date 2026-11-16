package com.example.rps;

import java.util.Random;

public class RandomMoveChooser implements MoveChooser{

    private final Random random;

    public RandomMoveChooser (Random random){
        this.random=random;
    }

    public RandomMoveChooser(){
        this(new Random());
    }

    @Override
    public Move choose() {
        return Move.ROCK;
    }
}

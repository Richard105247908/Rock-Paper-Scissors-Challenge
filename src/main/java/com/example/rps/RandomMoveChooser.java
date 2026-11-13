package com.example.rps;

import java.util.Random;

public class RandomMoveChooser implements MoveChooser{

    public RandomMoveChooser (Random random){
    }

    public RandomMoveChooser(){
        this(new Random());
    }
}

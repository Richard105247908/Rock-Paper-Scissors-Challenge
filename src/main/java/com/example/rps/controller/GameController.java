package com.example.rps.controller;

import com.example.rps.Game;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private final Game game;

    public GameController(Game game){
        this.game=game;
    }
}

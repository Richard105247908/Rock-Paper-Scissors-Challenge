package com.example.rps.controller;

import com.example.rps.Game;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    private final Game game;

    public GameController(Game game){
        this.game=game;
    }

    @GetMapping("/game")
   public String newGame(){
        return "new_game";
    }
}

package com.example.rps.controller;

import com.example.rps.Game;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @GetMapping("/game")
    public String createGame() {
        return "new_game";
    }

    @PostMapping("/game")
    public String newName(@RequestParam String name, HttpSession session) {
        session.setAttribute("name", name);
        return "redirect:/play";
    }

    @GetMapping("/play")
    public String getName(HttpSession session, Model model){
        model.addAttribute("name",session.getAttribute("name"));

        return "play";
    }

}

package com.example.rps.controller;

import com.example.rps.Game;
import com.example.rps.Move;
import com.example.rps.Player;
import com.example.rps.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

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
    public String startGame(@RequestParam String name, HttpSession session, Model model) {

        try {
            Player player = new Player(name);
            session.setAttribute("name", player.name());
            return "redirect:/play";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "new_game";
        }
    }

    @GetMapping("/play")
    public String play(HttpSession session, Model model) {
        String name = (String) session.getAttribute("name");
        if (name == null) {
            return "redirect:/game";
        }
        model.addAttribute("name", name);
        model.addAttribute("moves", Move.values());
        return "play";
    }

    @PostMapping("/play")
    public String playResults(@RequestParam Move move, HttpSession session, Model model) {
        String name = (String) session.getAttribute("name");
        if (name == null) {
            return "redirect:/game";
        }
        Result result = game.play(move).getResult();
        model.addAttribute("name", name);
        return result.name().toLowerCase(Locale.ROOT);
    }

}

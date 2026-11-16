package com.example.rps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {

    @Bean
    MoveChooser moveChooser() {
        return new RandomMoveChooser();
    }

    @Bean
    Game game(MoveChooser moveChooser) {
        return new Game(moveChooser);
    }
}
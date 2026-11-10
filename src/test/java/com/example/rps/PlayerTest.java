package com.example.rps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    @Test
    void playerKnowsTheirName(){
        Player player=new Player("Kevin");

        assertEquals("Kevin",player.name());
    }

    @Test
    void blankPlayerNameIsRejected(){
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(""));

        assertEquals("Player name must not be blank",ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
        void blankNameIsRejected(String name) {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> new Player(""));

        assertEquals("Player name must not be blank",ex.getMessage());

    }
}

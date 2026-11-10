package com.example.rps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    @Test
    void playerKnowsTheirName(){
        Player player=new Player("Kevin");

        assertEquals("Kevin",player.name());
    }

    @Test
    void playerReturnNameNull(){
        Player player1 = new Player("");
        var ex = assertThrows(IllegalArgumentException.class,
                () -> player1.isBlank();

        assertEquals("",ex.getMessage());
    }
}

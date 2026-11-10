package com.example.rps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    void playerKnowsTheirName(){
        Player player=new Player("Kevin");

        assertEquals("Kevin",player.name());
    }
}

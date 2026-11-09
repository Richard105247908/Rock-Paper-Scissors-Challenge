package com.example.rps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MoveTest {

    @Test
    void rockBeatsScissors() {
        assertTrue(Move.ROCK.beats(Move.SCISSORS));
    }

    @Test
    void rockDoesNotBeatPaper() {assertFalse(Move.ROCK.beats(Move.PAPER)); }

}

package com.example.rps;

// Where the computer's move comes from. Tests inject a fixed one so results are predictable.
// The real RandomMoveChooser (which uses java.util.Random) is something you write later.
public interface MoveChooser {
    Move choose();
}

package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.Playable;
import com.appdav.unknownrunner.gameobjects.Player;

public class FallAi implements Player {

    private Playable character;

    public FallAi(Playable character) {
        this.character = character;
    }

    @Override
    public void makeMove() {
        character.move(Move.FALL);
    }

    @Override
    public void releasePlayer() {
        this.character = null;
    }
}

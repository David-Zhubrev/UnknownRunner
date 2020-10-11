package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.Playable;
import com.appdav.unknownrunner.gameobjects.Player;

public class GolemAi implements Player {

    Playable character;

    public GolemAi(Playable character) {
        this.character = character;
    }

    @Override
    public void makeMove() {
        if (character == null) return;
        character.move(Move.FALL);
        character.move(Move.MOVE_LEFT);
    }

    @Override
    public void releasePlayer() {
        this.character = null;
    }
}

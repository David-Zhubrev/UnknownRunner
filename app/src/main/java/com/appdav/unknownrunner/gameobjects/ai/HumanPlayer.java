package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.gameobjects.Controller;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.Playable;
import com.appdav.unknownrunner.gameobjects.Player;

public class HumanPlayer implements Player, Controller {

    private Playable character;

    public HumanPlayer(Playable character) {
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

    @Override
    public void onClick() {
        if (character != null)
            character.move(Move.JUMP);
    }

    @Override
    public void onSwipeBottom() {
        if (character != null)
            character.move(Move.DROP);
    }


}

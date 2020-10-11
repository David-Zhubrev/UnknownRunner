package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.gameobjects.Controller;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.Playable;
import com.appdav.unknownrunner.gameobjects.Player;

public class HumanPlayer implements Player, Controller {

    private Playable character;
    private GameOverCallback callback;

    public HumanPlayer(Playable character, GameOverCallback callback){
        this.character = character;
        this.callback = callback;
    }

    @Override
    public void makeMove() {
        character.move(Move.FALL);
    }

    @Override
    public void releasePlayer() {
        this.character = null;
    }

    public void die(){
        callback.gameOver();
    }

    public void endGame(){
        callback.endGame();
    }


    @Override
    public void onLeftSideClick() {
        character.move(Move.SHOOT);
    }

    @Override
    public void onRightSideClick() {
        character.move(Move.JUMP);
    }

    public interface GameOverCallback{

        void gameOver();

        void endGame();
    }
}

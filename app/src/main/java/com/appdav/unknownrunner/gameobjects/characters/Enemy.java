package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.CollisionHandler;
import com.appdav.unknownrunner.tools.Screen;

public abstract class Enemy extends Character {

    protected boolean isAlive = true;

    public Enemy(Resources res, Speed speed, int downScale) {
        super(res, speed, downScale);
    }

    @Override
    void onUpdateBeforeCollisionHandling() {
        if (isJumping) nextMoves.add(Move.JUMP);
        for (Collision collision : collisions) {
            if (collision.source instanceof Projectile) {
                die();
            }
        }
        if (collisions != null){
            for (Collision c : collisions){
                if (c.source instanceof Platform && c.position == CollisionHandler.Position.LEFT){
                    nextMoves.add(Move.JUMP);
                }
            }
        }
    }

    @Override
    void onUpdateAfterCollisionHandling() {

        if (nextMoves.contains(Move.JUMP)) {
            if (y <= 0) {
                isJumping = false;
                currentVerticalSpeed = 0;
            } else {
                if (!isJumping) {
                    isJumping = true;
                    currentVerticalSpeed = -70;
                }
                JumpInterpolator.nextMove(this);
            }
        }
    }

    abstract public void win();

    abstract public void die();

}

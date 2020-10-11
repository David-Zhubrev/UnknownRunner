package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.tools.CollisionHandler;
import com.appdav.unknownrunner.tools.Screen;

public abstract class Enemy extends Character {

    protected boolean isAlive = true;

    public Enemy(Resources res, Speed speed, int downScale) {
        super(res, speed, downScale);
    }

    @Override
    void onUpdateBeforeCollisionHandling() {
        for (Collision collision : collisions) {
            if (collision.source instanceof Projectile) {
                die();
            }
        }
    }

    abstract public void win();

    abstract public void die();

}

package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collidable;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.tools.Screen;

public abstract class Collectible extends GameObject implements Collidable {

    protected Speed levelSpeed;

    private Rect collisionRect;

    public Collectible(Resources res, int downScale, Speed levelSpeed) {
        super(res, downScale);
        this.levelSpeed = levelSpeed;
    }

    public abstract int getPrice();

    @Override
    public void update() {
        collisionRect = null;
        x -= levelSpeed.speed - 10 * Screen.screenRatioX;
        if (x < -Screen.screenWidth / 2) destroy();
    }

    @Override
    public void onCollisionWith(Collision collision) {
    }

    @Override
    public Rect getCollisionRect() {
        if (collisionRect == null)
            collisionRect = new Rect(x, y, x + width, y + height);
        return collisionRect;
    }
}

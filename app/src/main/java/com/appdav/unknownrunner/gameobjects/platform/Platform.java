package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collidable;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.tools.Tools;


public abstract class Platform extends GameObject implements Collidable {

    protected final Speed speed;
    protected Rect collisionRect;

    public Platform(Resources res, Speed speed) {
        super(res, 1);
        this.speed = speed;
        Tools.blockHeight = this.height;
    }

    @Override
    public void update() {
        collisionRect = null;
        if (x < -width) {
            destroy();
            return;
        }
        x -= speed.speed;
    }


    @Override
    public void onCollisionWith(Collision collision) {
    }

    @Override
    public Rect getCollisionRect() {
        if (collisionRect == null) {
            collisionRect = new Rect(x, y, x + width, y + height);
        }
        return collisionRect;
    }


}

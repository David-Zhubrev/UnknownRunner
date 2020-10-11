package com.appdav.unknownrunner.gameobjects;

import android.graphics.Rect;

public interface Collidable {

    void onCollisionWith(Collision collision);

    Rect getCollisionRect();
}

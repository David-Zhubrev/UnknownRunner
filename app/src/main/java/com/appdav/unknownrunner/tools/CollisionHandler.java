package com.appdav.unknownrunner.tools;

import android.graphics.Rect;

import com.appdav.unknownrunner.gameobjects.Collidable;
import com.appdav.unknownrunner.gameobjects.Collision;

import static java.lang.Math.abs;

public class CollisionHandler {

    public enum Position {

        TOP,
        BOTTOM,
        LEFT,
        RIGHT,

    }

    public static void handleCollision(Collidable collidable1, Collidable collidable2) {
        Rect a = collidable1.getCollisionRect();
        if (a == null) return;
        Rect b = collidable2.getCollisionRect();
        if (b == null) return;
        Rect intersection = new Rect(a);
        if (intersection.intersect(b)) {
            Position vertical, horizontal, result;
            horizontal = intersection.left == b.left ? Position.RIGHT : Position.LEFT;
            vertical = intersection.top == b.top ? Position.BOTTOM : Position.TOP;
            result = (abs(intersection.left - intersection.right) > abs(intersection.top - intersection.bottom)) ?
                    vertical : horizontal;
            collidable1.onCollisionWith(new Collision(collidable2, result));
            collidable2.onCollisionWith(new Collision(collidable1, result));
        }
    }
}


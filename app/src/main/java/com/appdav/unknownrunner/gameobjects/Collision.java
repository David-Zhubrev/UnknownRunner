package com.appdav.unknownrunner.gameobjects;

import com.appdav.unknownrunner.tools.CollisionHandler;

public class Collision {

    public final Collidable source;
    public final CollisionHandler.Position position;

    public Collision(Collidable collidable, CollisionHandler.Position position){
        this.source = collidable;
        this.position = position;
    }

}

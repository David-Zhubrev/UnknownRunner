package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collidable;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.tools.CollisionHandler.Position;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.gameobjects.Playable;
import com.appdav.unknownrunner.gameobjects.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends GameObject implements Playable, Collidable {

    protected Rect collisionRect;
    protected List<Move> nextMoves;
    protected List<Collision> collisions;
    protected Speed speed;
    protected Player player;
    protected int thresholdTop = 0, thresholdBottom = 0, thresholdLeft = 0, thresholdRight = 0;
    protected int extraSpeed = 0;

    public Character(Resources res, Speed speed, int downScale) {
        super(res, downScale);
        this.speed = speed;
    }

    public void attachPlayer(Player player) {
        this.player = player;
    }

    public void detachPlayer() {
        this.player = null;
    }

    @Override
    public void destroy() {
        super.destroy();
        player.releasePlayer();
        detachPlayer();
    }

    @Override
    public void update() {
        super.update();
        if (isDestroyed) return;
        if (player != null) player.makeMove();
        collisionRect = null;
        onUpdateBeforeCollisionHandling();
        if (collisions != null && nextMoves != null && !collisions.isEmpty()) {
            for (Collision collision : collisions) {
                if (collision.source instanceof Platform) {
                    if (collision.position == Position.TOP) {
                        nextMoves.remove(Move.JUMP);
                    }
                    if (collision.position == Position.BOTTOM) {
                        nextMoves.remove(Move.FALL);
                    }
                    if (collision.position == Position.LEFT) {
                        nextMoves.add(Move.MOVE_RIGHT);
                    } else if (collision.position == Position.RIGHT) {
                        nextMoves.add(Move.MOVE_LEFT);
                    }

                }
            }
        }
        collisions = null;
        onUpdateAfterCollisionHandling();
        if (nextMoves != null && !nextMoves.isEmpty()) {
            for (Move move : nextMoves) {
                switch (move) {
                    case MOVE_RIGHT:
                        x += speed.speed + extraSpeed;
                        break;
                    case MOVE_UP:
                        y -= speed.speed + extraSpeed;
                        break;
                    case MOVE_DOWN:
                        y += speed.speed + extraSpeed;
                        break;
                    case MOVE_LEFT:
                        x -= speed.speed + extraSpeed;
                        break;
                    case FALL:
                        y += speed.speed + extraSpeed;
                        break;
                }
            }
        }
        onUpdateAfterMovementHandling();
        nextMoves = null;
    }

    abstract void onUpdateBeforeCollisionHandling();

    abstract void onUpdateAfterCollisionHandling();

    abstract void onUpdateAfterMovementHandling();

    @Override
    public void onCollisionWith(Collision collision) {
        if (collisions == null) collisions = new ArrayList<>();
        collisions.add(collision);
    }


    @Override
    public void move(Move nextMove) {
        if (this.nextMoves == null) this.nextMoves = new ArrayList<>();
        this.nextMoves.add(nextMove);
    }

    @Override
    public Rect getCollisionRect() {
        if (collisionRect == null) {
            collisionRect = new Rect(x + thresholdLeft, y + thresholdTop,
                    x + width - thresholdRight, y + height - thresholdBottom);
        }
        return collisionRect;
    }
}

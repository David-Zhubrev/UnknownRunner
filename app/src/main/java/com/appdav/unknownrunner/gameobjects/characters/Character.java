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
import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends GameObject implements Playable, Collidable {

    protected Rect collisionRect;
    protected List<Move> nextMoves;
    protected List<Collision> collisions;
    protected Speed speed;

    protected boolean isDead = false;

    protected float currentVerticalSpeed = 0;
    protected Player player;
    protected int thresholdTop = 0, thresholdBottom = 0, thresholdLeft = 0, thresholdRight = 0;
    protected int extraSpeed = 0;

    protected boolean isJumping = false;

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
        if (player != null) {
            player.releasePlayer();
            detachPlayer();
        }
    }

    @Override
    public void update() {
        super.update();
        if (player != null) {
            player.makeMove();
        } else {
            move(Move.FALL);
        }
        collisionRect = null;
        onUpdateBeforeCollisionHandling();
        if (collisions != null && nextMoves != null && !collisions.isEmpty()) {
            for (Collision collision : collisions) {
                if (collision.source instanceof Platform) {
                    if (collision.position == Position.TOP) {
                        nextMoves.remove(Move.JUMP);
                        isJumping = false;
                    }
                    if (collision.position == Position.BOTTOM) {
                        nextMoves.remove(Move.FALL);
                        if (!isJumping) currentVerticalSpeed = 0;
                    }
                    if (collision.position == Position.LEFT) {
                        nextMoves.add(Move.SHIFT_LEFT);
                        nextMoves.remove(Move.MOVE_LEFT);
                        nextMoves.remove(Move.MOVE_RIGHT);
                    } else if (collision.position == Position.RIGHT) {
                        nextMoves.add(Move.SHIFT_LEFT);
                        nextMoves.remove(Move.MOVE_LEFT);
                        nextMoves.remove(Move.MOVE_RIGHT);
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
                    case SHIFT_LEFT:
                        x -= speed.speed;
                        break;
                    case SHIFT_RIGHT:
                        x += speed.speed;
                        break;
                    case MOVE_LEFT:
                        x -= speed.speed + extraSpeed;
                        break;
                    case FALL:
                        JumpInterpolator.nextMove(this);
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


    protected static class JumpInterpolator {

        private static final int MAX_SPEED = 70;
        private static final int threshold = 10;

        public static void nextMove(Character character) {
            int g = 40;
            float time = 1f / 2.5f;
            character.currentVerticalSpeed += g / 10f;
            if (character.currentVerticalSpeed > 0 && character.currentVerticalSpeed < threshold) {
                character.currentVerticalSpeed = threshold;
            }
            if (character.currentVerticalSpeed >= 0) character.isJumping = false;
            if (character.y + character.height > Screen.screenHeight - Screen.screenHeight / 4 && character.currentVerticalSpeed > MAX_SPEED)
                character.currentVerticalSpeed = MAX_SPEED;
            int y = (int) (character.y + character.currentVerticalSpeed * time);
            character.y += character.currentVerticalSpeed * time;
        }

    }
}

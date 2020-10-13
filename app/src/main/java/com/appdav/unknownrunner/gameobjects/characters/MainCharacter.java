package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.gameobjects.ai.FallAi;
import com.appdav.unknownrunner.gameobjects.bitmapholders.MainCharacterBitmaps;
import com.appdav.unknownrunner.gameobjects.collectibles.Collectible;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.CollisionHandler;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.tools.Score;
import com.appdav.unknownrunner.tools.Screen;

public class MainCharacter extends Character implements GameObject.Callback {

    private FrameManager dyingFrameManager;
    private FrameManager deadFrameManager;

    private static final int initialPosition = Screen.screenWidth / 3;

    protected int jumpCounter = 0;
    private int deadFrameCounter = 0;

    private GameOverCallback callback;

    public MainCharacter(Resources res, Speed speed, GameOverCallback callback) {
        super(res, speed, 5);
        createSecondaryFrameManagers();
        this.extraSpeed = -speed.speed / 2;
        thresholdTop = height / 10;
        thresholdBottom = height / 5;
        thresholdLeft = thresholdRight = width / 4;
        this.callback = callback;
    }

    private void die() {
        if (isDead) return;
        currentFrameManager = dyingFrameManager;
        attachPlayer(new FallAi(this));
        callback.gameOver();
        isDead = true;
    }

    @Override
    public void update() {
        if (isDestroyed)
            callback.endGame();
        super.update();
    }

    @Override
    void onUpdateBeforeCollisionHandling() {
        if (x < -Screen.screenWidth / 2 || y + height / 2 > Screen.screenHeight) {
            die();
            return;
        } else if (x < initialPosition) {
            nextMoves.add(Move.MOVE_RIGHT);
        }
        if (nextMoves.contains(Move.DROP)) {
            isJumping = false;
            if (!nextMoves.contains(Move.FALL)) {
                nextMoves.add(Move.FALL);
            }
            currentVerticalSpeed = 200;
        }

        if (isJumping) nextMoves.add(Move.JUMP);
        if (collisions != null && !collisions.isEmpty()) {
            for (Collision collision : collisions) {
                if (collision.source instanceof Enemy) {
                    if (collision.position == CollisionHandler.Position.BOTTOM) {
                        ((Enemy) collision.source).die();
                        Score.score += 500;
                        nextMoves.add(Move.JUMP);
                    } else {
                        die();
                    }
                } else if (collision.source instanceof Platform) {
                    if (collision.position == CollisionHandler.Position.TOP) {
                        isJumping = false;
                        currentVerticalSpeed = 0;
                    }
                } else if (collision.source instanceof Collectible) {
                    Score.score += ((Collectible) collision.source).getPrice();
                    ((Collectible) collision.source).destroy();
                }
            }
        }
    }

    @Override
    public void onCollisionWith(Collision collision) {
        super.onCollisionWith(collision);
    }

    @Override
    void onUpdateAfterCollisionHandling() {
        if (this.player instanceof FallAi) {
            if (nextMoves == null || nextMoves.isEmpty()) {
                callback.endGame();
                return;
            }
        }
        if (nextMoves == null) return;
        if (!nextMoves.contains(Move.JUMP) && !nextMoves.contains(Move.FALL)) {
            jumpCounter = 0;
        }
        if (isJumping) {
            nextMoves.remove(Move.FALL);
        }
        if (nextMoves.contains(Move.JUMP)) {
            if (y <= 0) {
                isJumping = false;
                currentVerticalSpeed = 0;
            } else {
                if (!isJumping) {
                    isJumping = true;
                    currentVerticalSpeed = -70;
                    jumpCounter++;
                }
                JumpInterpolator.nextMove(this);
            }
        }
        nextMoves.remove(Move.SHIFT_LEFT);
    }

    @Override
    void onUpdateAfterMovementHandling() {
        if (y + height < 0) die();
    }

    private void createSecondaryFrameManagers() {
        dyingFrameManager = createFrameManager(MainCharacterBitmaps.Bitmaps.getDyingFrames(getResources()), this);
        deadFrameManager = createFrameManager(MainCharacterBitmaps.Bitmaps.getDeadFrames(getResources()), this);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(MainCharacterBitmaps.Bitmaps.getWakingFrames(getResources()), null);
    }


    @Override
    public void onLastFrameShown(FrameManager manager) {
        if (manager == dyingFrameManager) {
            currentFrameManager = deadFrameManager;
        }
        if (manager == deadFrameManager) {
            deadFrameCounter++;
            if (deadFrameCounter >= 60) {
                callback.endGame();
            }
        }
    }

    @Override
    public Rect getCollisionRect() {
        return super.getCollisionRect();
    }

    public interface GameOverCallback {

        void gameOver();

        void endGame();
    }
}

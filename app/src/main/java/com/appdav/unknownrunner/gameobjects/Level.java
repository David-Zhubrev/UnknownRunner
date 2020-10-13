package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.gameobjects.characters.Enemy;
import com.appdav.unknownrunner.gameobjects.characters.MainCharacter;
import com.appdav.unknownrunner.tools.CollisionHandler;

import java.util.List;

public abstract class Level implements GameDrawable, MainCharacter.GameOverCallback {

    protected List<GameDrawable> drawables;
    protected List<Collidable> collidables;
    protected List<Enemy> enemies;

    protected Background background;

    protected GroundGenerator groundGenerator;
    protected Ground ground;
    protected Resources res;
    protected Speed speed;

    protected HumanPlayer player;

    private UiGameplayCallback uiGameplayCallback;

    private boolean isDestroyed = false;

    public Level(Resources res, Speed speed, UiGameplayCallback listener) {
        this.res = res;
        this.speed = speed;
        initializeObjects();
        this.uiGameplayCallback = listener;
    }

    abstract protected void initializeObjects();

    public Controller getController() {
        return this.player;
    }

    @Override
    public void drawObject(Canvas canvas, Paint paint) {
        if (isDestroyed) return;
        for (GameDrawable drawable : drawables) {
            drawable.drawObject(canvas, paint);
        }
    }

    private void checkIfDrawablesValid() {
        if (drawables == null || drawables.size() == 0) return;
        for (int i = 0; i < (drawables != null ? drawables.size() : 0); i++) {
            GameDrawable drawable = drawables.get(i);
            if (drawable != null && drawable.isDestroyed()) {
                if (drawable instanceof Collidable) {
                    collidables.remove(drawable);
                }
                if (drawable instanceof Enemy) {
                    enemies.remove(drawable);
                }
                if (drawable instanceof MainCharacter) {
                    endGame();
                }
                if (drawables != null) drawables.remove(drawable);
            }
        }
    }


    @Override
    public void update() {
        if (isDestroyed) return;
        checkIfDrawablesValid();
        List<Collidable> platforms = null;
        if (ground != null) {
            platforms = ground.getCollidables();
            collidables.addAll(platforms);
        }
        if (collidables != null) {
            int size = collidables.size();
            if (size > 1) {
                for (int i = 0; i < size; i++) {
                    for (int j = i + 1; j < size; j++) {
                        CollisionHandler.handleCollision(collidables.get(i), collidables.get(j));
                    }
                }
            }
            if (platforms != null) collidables.removeAll(platforms);
        }
        if (drawables != null) {
            for (GameDrawable drawable : drawables) {
                drawable.update();
            }
        }
    }

    @Override
    public void gameOver() {
        speed.speed = 0;
        for (Enemy enemy : enemies) {
            enemy.win();
        }
    }

    @Override
    public void endGame() {
        uiGameplayCallback.onGameOver();
        this.destroy();
    }


    @Override
    public void destroy() {
        if (drawables != null) {
            for (GameDrawable drawable : drawables) {
                drawable.destroy();
            }
        }
        drawables = null;
        collidables = null;
        enemies = null;
        if (player != null)
            player.releasePlayer();
        player = null;
        ground = null;
        groundGenerator = null;
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public interface UiGameplayCallback {

        void onGameOver();

    }
}
package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.gameobjects.characters.Enemy;
import com.appdav.unknownrunner.tools.CollisionHandler;

import java.util.List;

public abstract class Level implements GameDrawable, HumanPlayer.GameOverCallback {

    protected List<GameDrawable> drawables;
    protected List<Collidable> collidables;
    protected List<Enemy> enemies;

    protected Background background;

    protected GroundGenerator groundGenerator;
    protected Ground ground;
    protected Resources res;
    protected Speed speed;

    protected HumanPlayer player;

    private StopThreadListener stopThreadListener;

    private boolean isDestroyed = false;

    public Level(Resources res, Speed speed, StopThreadListener listener) {
        this.res = res;
        this.speed = speed;
        initializeObjects();
        this.stopThreadListener = listener;
    }

    abstract protected void initializeObjects();

    public Controller getController() {
        return this.player;
    }

    @Override
    public void drawObject(Canvas canvas, Paint paint) {
        for (GameDrawable drawable : drawables) {
            drawable.drawObject(canvas, paint);
        }
    }

    private void checkIfDrawablesValid() {
        for (int i = 0; i < drawables.size(); i++) {
            GameDrawable drawable = drawables.get(i);
            if (drawable.isDestroyed()) {
                if (drawable instanceof Collidable) {
                    collidables.remove(drawable);
                }
                if (drawable instanceof Enemy) {
                    enemies.remove(drawable);
                }
                drawables.remove(drawable);
            }
        }
    }


    @Override
    public void update() {
        checkIfDrawablesValid();
        List<Collidable> platforms = ground.getCollidables();
        collidables.addAll(platforms);
        int size = collidables.size();
        if (size > 1) {
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    CollisionHandler.handleCollision(collidables.get(i), collidables.get(j));
                }
            }
        }
        collidables.removeAll(platforms);
        for (GameDrawable drawable : drawables) {
            drawable.update();
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
        this.destroy();
        stopThreadListener.onThreadShouldBeStopped();
    }


    @Override
    public void destroy() {
        drawables = null;
        collidables = null;
        enemies = null;
        player = null;
        ground = null;
        groundGenerator = null;
        res = null;
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public interface StopThreadListener {

        void onThreadShouldBeStopped();

    }
}
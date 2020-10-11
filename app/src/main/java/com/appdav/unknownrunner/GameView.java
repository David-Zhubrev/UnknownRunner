package com.appdav.unknownrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.appdav.unknownrunner.gameobjects.Controller;
import com.appdav.unknownrunner.gameobjects.GameDrawable;
import com.appdav.unknownrunner.gameobjects.Level;
import com.appdav.unknownrunner.gameobjects.MountainLevel;
import com.appdav.unknownrunner.gameobjects.Player;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.tools.Screen;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private GameDrawable currentLevel;
    private Paint paint;

    private Controller controller;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        Level level = new MountainLevel(getResources(), this::stopThread);
        this.currentLevel = level;
        this.controller = level.getController();
        thread = new GameThread(getHolder(), this);
        this.paint = new Paint();
        setFocusable(true);
    }

    public GameDrawable getLevel() {
        return currentLevel;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        currentLevel.drawObject(canvas, paint);
    }

    private void stopThread() {
        currentLevel.destroy();
        currentLevel = null;
        controller = null;
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (event.getX() < Screen.screenWidth / 2f) {
                    controller.onLeftSideClick();
                } else controller.onRightSideClick();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getX() > Screen.screenWidth / 2f) {
                    controller.onRightSideClick();
                } else controller.onLeftSideClick();
                break;
        }
        return true;
    }
}

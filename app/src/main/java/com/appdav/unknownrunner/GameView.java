package com.appdav.unknownrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.appdav.unknownrunner.gameobjects.Controller;
import com.appdav.unknownrunner.gameobjects.GameDrawable;
import com.appdav.unknownrunner.gameobjects.Level;
import com.appdav.unknownrunner.gameobjects.MountainLevel;
import com.appdav.unknownrunner.tools.OnSwipeTouchListener;
import com.appdav.unknownrunner.tools.Screen;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Level.UiGameplayCallback {

    private GameThread thread;
    private Level currentLevel;
    private Paint paint;


    private GameActivityCallback callback;

    private Controller controller;

    private boolean isRunning;

    private boolean isInitialized = false;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void attachCallback(GameActivityCallback callback) {
        this.callback = callback;
    }

    public void detachCallback() {
        this.callback = null;
    }

    private void init() {
        getHolder().addCallback(this);
        Level level = new MountainLevel(getResources(), this);
        this.currentLevel = level;
        this.controller = level.getController();
        setOnTouchListener(new OnSwipeTouchListener(this.getContext(), new OnSwipeTouchListener.Callback() {
            @Override
            public void onSwipeBottom() {
                controller.onSwipeBottom();
            }
        }));
        this.paint = new Paint();
        setFocusable(true);
        isInitialized = true;
    }

    public GameDrawable getLevel() {
        return currentLevel;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        currentLevel.drawObject(canvas, paint);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopThread() {
        controller = null;
        if (thread != null) {
            thread.setRunning(false);
        }
        callback.onGamePaused();
    }

    public void startThread() {
        if (!isInitialized) init();
        controller = currentLevel.getController();
        thread = new GameThread(getHolder(), this);
        isRunning = true;
        thread.setRunning(true);
        thread.start();
        while (true){
            if (thread.getFrameCount() > 3){
                callback.onGameStart();
                return;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        destroy();
    }

    public void destroy() {
        if (isRunning) stopThread();
        controller = null;
        isInitialized = false;
        if (currentLevel != null) {
            if (!currentLevel.isDestroyed()) {
                currentLevel.destroy();
                currentLevel = null;
            }
        }
        getHolder().removeCallback(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (controller == null) return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (event.getX() < Screen.screenWidth / 2f) {
                    controller.onLeftSideClick();
                } else controller.onRightSideClick();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getX() > Screen.screenWidth / 2f) {
                    controller.onLeftSideClick();
                } else controller.onRightSideClick();
                break;
        }
        return true;
    }


    public void restart() {
        this.currentLevel = new MountainLevel(getResources(), this);
        startThread();
    }

    @Override
    public void onGameOver() {
        stopThread();
        if (callback != null) callback.onGameOverScreenShow();
    }

    public interface GameActivityCallback {
        void onGameOverScreenShow();

        void onGameStart();

        void onGamePaused();
    }


}

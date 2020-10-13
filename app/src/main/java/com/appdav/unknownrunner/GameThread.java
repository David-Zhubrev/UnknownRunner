package com.appdav.unknownrunner;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.appdav.unknownrunner.gameobjects.GameDrawable;
import com.appdav.unknownrunner.gameobjects.Level;
import com.appdav.unknownrunner.tools.Tools;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private GameDrawable currentLevel;
    private boolean isRunning = false;
    private int frameCount = 0;

    private final static int WAIT_TIME_LIMIT = 1000;

    private static final int targetFps = Tools.Fps.getTargetFps();

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public GameThread(SurfaceHolder holder, GameView gameView) {
        this.surfaceHolder = holder;
        this.gameView = gameView;
        this.currentLevel = gameView.getLevel();
    }

    public int getFrameCount(){
        return frameCount;
    }

    @Override
    public void run() {
        long startTime;
        long drawTime;
        long waitTime;
        long totalTime = 0;
        long targetTime = 1000 / targetFps;
        while (isRunning && currentLevel != null && !currentLevel.isDestroyed()) {
            if (surfaceHolder == null || !surfaceHolder.getSurface().isValid()) continue;
            startTime = System.nanoTime();
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas == null) continue;
            if (currentLevel == null || currentLevel.isDestroyed()) break;
            currentLevel.update();
            if (gameView == null) break;
            gameView.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            drawTime = (System.nanoTime() - startTime) / 1_000_000;
            waitTime = targetTime - drawTime;
            if (waitTime > WAIT_TIME_LIMIT) continue;
            if (waitTime > 0) {
                try {
                    sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            frameCount++;
            totalTime += System.nanoTime() - startTime;
//            if (frameCount == targetFps){
//                double averageFps = 1000d / ((double) (totalTime / frameCount) / 1_000_000d);
//                frameCount = 0;
//                totalTime = 0;
//                Tools.Fps.setAverageFps(averageFps);
//            }
            if (totalTime >= 1_000_000_000) {
                Tools.Fps.setCurrentFps(frameCount);
                frameCount = 0;
                totalTime = 0;
            }

            System.out.println(Tools.Fps.getCurrentFps());
        }

    }

}

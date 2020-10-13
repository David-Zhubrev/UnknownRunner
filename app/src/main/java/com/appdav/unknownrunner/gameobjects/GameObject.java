package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import static com.appdav.unknownrunner.tools.Screen.*;

public abstract class GameObject implements GameDrawable {

    protected int x, y,
            width = 0,
            height = 0;
    protected FrameManager currentFrameManager;
    protected boolean isDestroyed = false;
    private final Resources res;
    protected final int downScale;

    private List<FrameManager> managers;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public GameObject(Resources res, int downScale) {
        this.res = res;
        this.downScale = downScale;
        currentFrameManager = createMainFrameManager();
        if (width == 0 || height == 0) {
            width = currentFrameManager.frameWidth;
            height = currentFrameManager.frameHeight;
        }

    }

    protected abstract FrameManager createMainFrameManager();

    protected Resources getResources() {
        return res;
    }

    protected FrameManager createFrameManager(List<Bitmap> bitmaps, @Nullable Callback callback) {
        return new FrameManager(bitmaps, callback);
    }

//    protected FrameManager createFrameManager(Function<Resources, List<Bitmap>> function, @Nullable Callback callback) {
//        return new FrameManager(function.apply(res), callback);
//    }

    @Override
    public void update() {
        if (isDestroyed) return;
        if (x < -(screenWidth / 2) || x > screenWidth * 1.5) {
            destroy();
        }
        if (y < -(screenHeight) || y > screenHeight * 1.2) {
            destroy();
        }
    }

    @Override
    public void drawObject(Canvas canvas, Paint paint) {
        if (isDestroyed) return;
        if (currentFrameManager != null) {
            canvas.drawBitmap(currentFrameManager.getNextFrame(), x, y, paint);
        }
    }

    @Override
    public void destroy() {
        currentFrameManager = null;
        if (managers != null) {
            for (FrameManager manager : managers) {
                manager.detachCallback();
            }
            managers = null;
        }
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public class FrameManager {
        private int currentFrame = 0;
        private final int lastFrame;
        private final List<Bitmap> frames;

        private int frameWidth, frameHeight;

        private Callback callback;

        public FrameManager(@NonNull List<Bitmap> frames, @Nullable Callback callback) {
            this.frames = frames;
            this.lastFrame = frames.size() - 1;
            isDestroyed = false;
            this.callback = callback;
            setupWidthAndHeight();
        }

        private void setupWidthAndHeight() {
            Bitmap frame = frames.get(0);
            frameWidth = frame.getWidth();
            frameHeight = frame.getHeight();
        }


        public boolean hasCallback() {
            return this.callback != null;
        }

        public void detachCallback() {
            this.callback = null;
        }


        Bitmap getNextFrame() {
            if (currentFrame == lastFrame) {
                currentFrame = 0;
                if (callback != null) callback.onLastFrameShown(this);
                return frames.get(lastFrame);
            } else return frames.get(currentFrame++);
        }
    }

    public interface Callback {
        void onLastFrameShown(FrameManager manager);
    }


}

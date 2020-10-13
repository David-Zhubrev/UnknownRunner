package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

import static com.appdav.unknownrunner.tools.Screen.*;

public abstract class GameObject implements GameDrawable {

    protected int x, y,
            width = 0,
            height = 0;
    protected FrameManager currentFrameManager;
    protected boolean isDestroyed = false;
    private final Resources res;
    private boolean toScale = false;
    protected final int downScale;

    private List<FrameManager> managers;

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
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

    private void addFrameManagerToList(FrameManager manager) {
        if (managers == null) managers = new ArrayList<>();
        managers.add(manager);
    }

    protected abstract FrameManager createMainFrameManager();

    private void setupWidthAndHeight(Bitmap frame) {
        width = frame.getWidth();
        height = frame.getHeight();
        int modificationCounter = 0;
        if (downScale != 1) {
            modificationCounter++;
            width /= downScale;
            height /= downScale;
        }
        if (screenRatioX != 1 && screenRatioY != 1) {
            modificationCounter++;
            width = (int) (width * screenRatioX);
            height = (int) (width * screenRatioY);
        }
        if (modificationCounter > 0) {
            toScale = true;
        }
    }


    protected FrameManager createFrameManager(List<Integer> resIds, @Nullable Callback callback) {
        List<Bitmap> frames = new ArrayList<>();
        for (int resId : resIds) {
            Bitmap frame = BitmapFactory.decodeResource(res, resId);
            if (width == 0 || height == 0) {
                setupWidthAndHeight(frame);
            }
            if (toScale) frame = Bitmap.createScaledBitmap(frame, width, height, false);
            frames.add(frame);
        }
        FrameManager result = new FrameManager(frames, callback);
        result.frameWidth = width;
        result.frameHeight = height;
        addFrameManagerToList(result);
        return result;
    }

    protected FrameManager createFrameManager(@DrawableRes int resId, Callback callback) {
        Bitmap frame = BitmapFactory.decodeResource(res, resId);
        if (width == 0 || height == 0) {
            setupWidthAndHeight(frame);
        }
        if (toScale) frame = Bitmap.createScaledBitmap(frame, width, height, false);
        FrameManager result = new FrameManager(frame, callback);
        result.frameWidth = width;
        result.frameHeight = height;
        addFrameManagerToList(result);
        return result;
    }

    protected FrameManager createFrameManager(@DrawableRes int resId) {
        return createFrameManager(resId, null);
    }

    protected FrameManager createFrameManager(List<Integer> resIds, boolean toMirror) {
        return createFrameManager(resIds, null, toMirror);
    }


    protected FrameManager createFrameManager(List<Integer> resIds, @Nullable Callback callback, boolean toMirror) {
        if (!toMirror) return createFrameManager(resIds, callback);
        List<Bitmap> frames = new ArrayList<>();
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        for (int resId : resIds) {
            Bitmap frame = BitmapFactory.decodeResource(res, resId);
            frame = Bitmap.createBitmap(frame, 0, 0, frame.getWidth(), frame.getHeight(), m, false);
            if (this.width == 0 || this.height == 0)
                setupWidthAndHeight(frame);
            if (toScale) frame = Bitmap.createScaledBitmap(frame, width, height, false);
            frames.add(frame);
        }
        FrameManager result = new FrameManager(frames, callback);
        result.frameWidth = width;
        result.frameHeight = height;
        addFrameManagerToList(result);
        return result;
    }

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
            if (callback != null) this.callback = callback;
        }

        public FrameManager(@NonNull Bitmap frame, @Nullable Callback callback) {
            this.frames = new ArrayList<>();
            frames.add(frame);
            this.lastFrame = frames.size() - 1;
            this.callback = callback;
        }

        public void attachCallback(Callback callback) {
            this.callback = callback;
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

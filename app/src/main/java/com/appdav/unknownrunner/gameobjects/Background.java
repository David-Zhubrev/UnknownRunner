package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.DrawableRes;

import com.appdav.unknownrunner.tools.Screen;

import java.util.List;

public abstract class Background implements GameDrawable {

    protected List<Layer> layers;
    protected Resources res;
    protected boolean isDestroyed;

    protected Background(Resources res, List<Layer> layers) {
        this.res = res;
        this.layers = layers;
    }

    @Override
    public void update() {
        for (Layer layer : layers) {
            layer.update();
        }
    }

    @Override
    public void drawObject(Canvas canvas, Paint paint) {
        for (Layer layer : layers) {
            layer.drawObject(canvas, paint);
        }
    }

    @Override
    public void destroy() {
        layers = null;
        res = null;
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    static class Layer implements GameDrawable {

        private int x, y, xSecond, xThird;
        private int width;
        private Bitmap bitmap;
        private boolean isStatic = false;
        private boolean isDestroyed = false;

        private int speed;

        Layer(Resources res, @DrawableRes int resId, int speed, boolean useFilter) {
            width = Screen.screenWidth;
            int height = Screen.screenHeight;
            x = 0;
            y = 0;
            if (speed == 0) {
                isStatic = true;
            }
            xSecond = x + width;
            if (!isStatic) {
                this.speed = (int) (speed * Screen.screenRatioX);
                xThird = xSecond + width;
            }
            bitmap = BitmapFactory.decodeResource(res, resId);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, useFilter);
        }

        @Override
        public void drawObject(Canvas canvas, Paint paint) {
            if (bitmap == null) return;
            if (!isStatic) {
                canvas.drawBitmap(bitmap, x, y, paint);
                canvas.drawBitmap(bitmap, xSecond, y, paint);
                canvas.drawBitmap(bitmap, xThird, y, paint);
            } else {
                canvas.drawBitmap(bitmap, 0, 0, paint);
                canvas.drawBitmap(bitmap, xSecond, 0, paint);
            }
        }

        @Override
        public void update() {
            if (!isStatic) {
                if (x + width < 0) x = xThird + width;
                if (xSecond + width < 0) xSecond = x + width - speed;
                if (xThird + width < 0) xThird = xSecond + width - speed;
                x -= speed;
                xSecond -= speed;
                xThird -= speed;
            }
        }

        @Override
        public void destroy() {
            this.bitmap = null;
            isDestroyed = true;
        }

        @Override
        public boolean isDestroyed() {
            return isDestroyed;
        }
    }

}

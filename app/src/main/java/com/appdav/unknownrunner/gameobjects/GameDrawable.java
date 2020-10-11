package com.appdav.unknownrunner.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface GameDrawable {

    void drawObject(Canvas canvas, Paint paint);
    void update();
    void destroy();

    boolean isDestroyed();

}

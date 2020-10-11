package com.appdav.unknownrunner.tools;

import android.graphics.Point;

public class Screen {

    public static int screenWidth, screenHeight;
    public static float screenRatioX, screenRatioY;

    private static final float myScreenWidth = 1560;
    private static final float myScreenHeight = 720;

    public static void setup(Point point) {
        screenHeight = point.y;
        screenWidth = point.x;
        screenRatioX = myScreenWidth / screenWidth;
        screenRatioY = myScreenHeight / screenHeight;
    }

}

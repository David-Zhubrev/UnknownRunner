package com.appdav.unknownrunner.gameobjects.bitmapholders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class GemBitmaps {

    public static int width = 0;
    public static int height = 0;

    private static final int downScale = 10;

    private static List<Bitmap> redGemFrames;
    private static List<Bitmap> blueGemFrames;
    private static List<Bitmap> greenGemFrames;

    public static List<Bitmap> getRedGemFrames(Resources res) {
        if (redGemFrames == null) {
            redGemFrames = createList(res, getRedGemResIds());
        }
        return redGemFrames;
    }

    public static List<Bitmap> getBlueGemFrames(Resources res) {
        if (blueGemFrames == null) {
            blueGemFrames = createList(res, getBlueGemResIds());
        }
        return blueGemFrames;
    }

    public static List<Bitmap> getGreenGemFrames(Resources res) {
        if (greenGemFrames == null) {
            greenGemFrames = createList(res, getGreenGemResIds());
        }
        return greenGemFrames;
    }

    private static List<Bitmap> createList(Resources res, List<Integer> resIds) {
        List<Bitmap> list = new ArrayList<>();
        if (width == 0 || height == 0) {
            Bitmap firstFrame = BitmapFactory.decodeResource(res, resIds.get(0));
            setupWidthAndHeight(firstFrame);
        }
        for (Integer resId : resIds) {
            Bitmap frame = BitmapFactory.decodeResource(res, resId);
            frame = Bitmap.createScaledBitmap(frame, width, height, false);
            list.add(frame);
        }
        return list;
    }

    private static void setupWidthAndHeight(Bitmap frame) {
        width = frame.getWidth();
        height = frame.getHeight();
        width /= downScale;
        height /= downScale;
        width = (int) (width * Screen.screenRatioX);
        height = (int) (height * Screen.screenRatioX);
    }

    private static List<Integer> getRedGemResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.red_gem);
        return resIds;
    }

    private static List<Integer> getBlueGemResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.blue_gem);
        return resIds;
    }

    private static List<Integer> getGreenGemResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.green_gem);
        return resIds;
    }

}

package com.appdav.unknownrunner.gameobjects.bitmapholders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class PlatformBitmaps {


    public static int width = 0;
    public static int height = 0;

    private static List<Bitmap> gapFrames;
    private static List<Bitmap> leftEdgeFrames;
    private static List<Bitmap> rightEdgeFrames;
    private static List<Bitmap> groundEdgeFrames;

    public static List<Bitmap> getGapFrames(Resources res) {
        if (gapFrames == null) {
            gapFrames = createList(res, getGapResIds());
        }
        return gapFrames;
    }

    public static List<Bitmap> getGroundEdgeFrames(Resources res) {
        if (groundEdgeFrames == null) {
            groundEdgeFrames = createList(res, getGroundResIds());
        }
        return groundEdgeFrames;
    }

    public static List<Bitmap> getLeftEdgeFrames(Resources res) {
        if (leftEdgeFrames == null) {
            leftEdgeFrames = createList(res, getLeftEdgeResIds());
        }
        return leftEdgeFrames;
    }

    public static List<Bitmap> getRightEdgeFrames(Resources res) {
        if (rightEdgeFrames == null) {
            rightEdgeFrames = createList(res, getRightEdgeResIds());
        }
        return rightEdgeFrames;
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
        width = (int) (width * Screen.screenRatioX);
        height = (int) (height * Screen.screenRatioX);
    }

    private static List<Integer> getGapResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.gap);
        return resIds;
    }

    private static List<Integer> getGroundResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.tile28);
        return resIds;
    }

    private static List<Integer> getLeftEdgeResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.tile_left_edge);
        return resIds;
    }

    private static List<Integer> getRightEdgeResIds() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.tile_right_edge);
        return resIds;
    }

}

package com.appdav.unknownrunner.gameobjects.bitmapholders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class MainCharacterBitmaps {

    public static class Bitmaps {

        public static int width = 0;
        public static int height = 0;

        private static final int downScale = 5;

        private static List<Bitmap> walkingFrames;
        private static List<Bitmap> dyingFrames;
        private static List<Bitmap> deadFrames;

        public static List<Bitmap> getWakingFrames(Resources res) {
            if (walkingFrames == null) {
                walkingFrames = createList(res, getWalkingResIds());
            }
            return walkingFrames;
        }

        public static List<Bitmap> getDyingFrames(Resources res) {
            if (dyingFrames == null) {
                dyingFrames = createList(res, getDyingResIds());
            }
            return dyingFrames;
        }

        public static List<Bitmap> getDeadFrames(Resources res) {
            if (deadFrames == null) {
                deadFrames = createList(res, getDeadResIds());
            }
            return deadFrames;
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

        private static List<Integer> getWalkingResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.wraith_03_moving_forward_000);
            resIds.add(R.drawable.wraith_03_moving_forward_001);
            resIds.add(R.drawable.wraith_03_moving_forward_002);
            resIds.add(R.drawable.wraith_03_moving_forward_003);
            resIds.add(R.drawable.wraith_03_moving_forward_004);
            resIds.add(R.drawable.wraith_03_moving_forward_005);
            resIds.add(R.drawable.wraith_03_moving_forward_006);
            resIds.add(R.drawable.wraith_03_moving_forward_007);
            resIds.add(R.drawable.wraith_03_moving_forward_008);
            resIds.add(R.drawable.wraith_03_moving_forward_009);
            resIds.add(R.drawable.wraith_03_moving_forward_010);
            resIds.add(R.drawable.wraith_03_moving_forward_011);
            return resIds;
        }

        private static List<Integer> getDyingResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.wraith_03_dying_000);
            resIds.add(R.drawable.wraith_03_dying_001);
            resIds.add(R.drawable.wraith_03_dying_002);
            resIds.add(R.drawable.wraith_03_dying_003);
            resIds.add(R.drawable.wraith_03_dying_004);
            resIds.add(R.drawable.wraith_03_dying_005);
            resIds.add(R.drawable.wraith_03_dying_006);
            resIds.add(R.drawable.wraith_03_dying_007);
            resIds.add(R.drawable.wraith_03_dying_008);
            resIds.add(R.drawable.wraith_03_dying_009);
            resIds.add(R.drawable.wraith_03_dying_010);
            resIds.add(R.drawable.wraith_03_dying_011);
            resIds.add(R.drawable.wraith_03_dying_012);
            resIds.add(R.drawable.wraith_03_dying_013);
            resIds.add(R.drawable.wraith_03_dying_014);
            return resIds;
        }

        private static List<Integer> getDeadResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.wraith_03_dying_014);
            return resIds;
        }
    }
}

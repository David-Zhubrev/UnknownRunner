package com.appdav.unknownrunner.gameobjects.bitmapholders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class GolemBitmaps {

    public static class Bitmaps {

        public static int width = 0;
        public static int height = 0;

        private static final int downScale = 10;

        private static List<Bitmap> walkingFrames;
        private static List<Bitmap> dyingFrames;
        private static List<Bitmap> fallingFrames;
        private static List<Bitmap> idleFrames;

        public static List<Bitmap> getWakingFrames(Resources res) {
            if (walkingFrames == null) {
                walkingFrames = createList(res, getWalkingResIds());
            }
            return walkingFrames;
        }


//        public Function<Resources, List<Bitmap>> getFrames = res -> {
//            if (mainFrames == null) {
//                mainFrames = createList(res, getMainResIds());
//            }
//            return mainFrames;
//        };

        public static List<Bitmap> getFallingFrames(Resources res) {
            if (fallingFrames == null) fallingFrames = createList(res, getFallingResIds());
            return fallingFrames;
        }

        public static List<Bitmap> getIdleFrames(Resources res) {
            if (idleFrames == null) idleFrames = createList(res, getidleResIds());
            return idleFrames;
        }

        public static List<Bitmap> getDyingFrames(Resources res) {
            if (dyingFrames == null) {
                dyingFrames = createList(res, getDyingResIds());
            }
            return dyingFrames;
        }

        private static List<Bitmap> mirror(List<Bitmap> source) {
            Matrix m = new Matrix();
            List<Bitmap> result = new ArrayList<>();
            m.preScale(-1, 1);
            for (Bitmap b : source) {
                result.add(Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false));
            }
            return result;
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
            return mirror(list);
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
            resIds.add(R.drawable.golem1_running_000);
            resIds.add(R.drawable.golem1_running_001);
            resIds.add(R.drawable.golem1_running_002);
            resIds.add(R.drawable.golem1_running_003);
            resIds.add(R.drawable.golem1_running_004);
            resIds.add(R.drawable.golem1_running_005);
            resIds.add(R.drawable.golem1_running_006);
            resIds.add(R.drawable.golem1_running_007);
            resIds.add(R.drawable.golem1_running_008);
            resIds.add(R.drawable.golem1_running_009);
            resIds.add(R.drawable.golem1_running_010);
            resIds.add(R.drawable.golem1_running_011);
            return resIds;
        }

        private static List<Integer> getDyingResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.golem1_dying_000);
            resIds.add(R.drawable.golem1_dying_001);
            resIds.add(R.drawable.golem1_dying_002);
            resIds.add(R.drawable.golem1_dying_003);
            resIds.add(R.drawable.golem1_dying_004);
            resIds.add(R.drawable.golem1_dying_005);
            resIds.add(R.drawable.golem1_dying_006);
            resIds.add(R.drawable.golem1_dying_007);
            resIds.add(R.drawable.golem1_dying_008);
            resIds.add(R.drawable.golem1_dying_009);
            resIds.add(R.drawable.golem1_dying_010);
            resIds.add(R.drawable.golem1_dying_011);
            resIds.add(R.drawable.golem1_dying_012);
            resIds.add(R.drawable.golem1_dying_013);
            resIds.add(R.drawable.golem1_dying_014);
            return resIds;
        }

        private static List<Integer> getFallingResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.golem1_falling_down_000);
            resIds.add(R.drawable.golem1_falling_down_001);
            resIds.add(R.drawable.golem1_falling_down_002);
            resIds.add(R.drawable.golem1_falling_down_003);
            resIds.add(R.drawable.golem1_falling_down_004);
            resIds.add(R.drawable.golem1_falling_down_005);
            return resIds;
        }

        private static List<Integer> getidleResIds() {
            List<Integer> resIds = new ArrayList<>();
            resIds.add(R.drawable.golem1_idle_blinking_000);
            resIds.add(R.drawable.golem1_idle_blinking_001);
            resIds.add(R.drawable.golem1_idle_blinking_002);
            resIds.add(R.drawable.golem1_idle_blinking_003);
            resIds.add(R.drawable.golem1_idle_blinking_004);
            resIds.add(R.drawable.golem1_idle_blinking_005);
            resIds.add(R.drawable.golem1_idle_blinking_006);
            resIds.add(R.drawable.golem1_idle_blinking_007);
            resIds.add(R.drawable.golem1_idle_blinking_008);
            resIds.add(R.drawable.golem1_idle_blinking_009);
            resIds.add(R.drawable.golem1_idle_blinking_010);
            resIds.add(R.drawable.golem1_idle_blinking_011);
            resIds.add(R.drawable.golem1_idle_blinking_012);
            resIds.add(R.drawable.golem1_idle_blinking_013);
            resIds.add(R.drawable.golem1_idle_blinking_014);
            resIds.add(R.drawable.golem1_idle_blinking_015);
            resIds.add(R.drawable.golem1_idle_blinking_016);
            resIds.add(R.drawable.golem1_idle_blinking_017);
            return resIds;
        }

    }

}

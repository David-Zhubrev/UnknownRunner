package com.appdav.unknownrunner.tools;

import java.util.Random;

public class Tools {

    public static final Random random = new Random();

    public static int blockHeight;

    public static class Fps {

        private static int currentFps;

        private final static int targetFps = 60;


        public static int getTargetFps() {
            return targetFps;
        }

        public static int getCurrentFps() {
            return currentFps;
        }

        public static void setCurrentFps(int currentFps) {
            Fps.currentFps = currentFps;
        }

    }

}

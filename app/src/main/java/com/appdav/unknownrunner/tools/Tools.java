package com.appdav.unknownrunner.tools;

import java.util.Random;

public class Tools {

    public static final Random random = new Random();

    public static class Fps {

        private static int currentFps;

        private static double averageFps;

        private final static int targetFps = 60;

        public static int getTargetFps() {
            return targetFps;
        }

        public static int getCurrentFps(){
            return currentFps;
        }

        public static double getAverageFps(){
            return averageFps;
        }

        public static void setCurrentFps(int currentFps) {
            Fps.currentFps = currentFps;
        }

        public static void setAverageFps(double averageFps) {
            Fps.averageFps = averageFps;
        }
    }

}

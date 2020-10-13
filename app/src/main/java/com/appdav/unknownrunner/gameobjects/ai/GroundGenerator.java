package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroundGenerator {

    public enum PlatformType {
        GAP,
        GROUND,
        HIGH,
        HIGH_WITH_GROUND,
        RIGHT_EDGE,
        LEFT_EDGE,
        RIGHT_EDGE_HIGH,
        LEFT_EDGE_HIGH,
        RIGHT_EDGE_HIGH_WITH_GROUND,
        LEFT_EDGE_HIGH_WITH_GROUND
    }

    protected GroundGenerationPattern previousPattern;
    protected final Random random;
    protected int length;

    public GroundGenerator() {
        this.random = Tools.random;
        length = (int) (Screen.screenWidth * 2);
    }

    public GroundGenerationPattern nextPattern(int fromX, int blockWidth) {
        int count = (fromX + length) / blockWidth;
        List<PlatformType> result = new ArrayList<>();
        int bound = 1;
        int latency = 0;
        int currentLength = 0;
        List<PlatformType> pattern;
        while (currentLength <= count) {
            int randomResult = random.nextInt(bound);
            switch (randomResult) {
                case 1:
                    pattern = createGap();
                    result.addAll(pattern);
                    currentLength += pattern.size();
                    latency = 2;
                    bound = 1;
                    break;
                case 2:
                    pattern = createHighPlatformWithGap(3 + random.nextInt(2));
                    result.addAll(pattern);
                    currentLength += pattern.size();
                    latency = 3;
                    bound = 1;
                    break;
                case 3:
                    pattern = createHighPlatformWithGround(3 + random.nextInt(2));
                    result.addAll(pattern);
                    currentLength += pattern.size();
                    latency = 0;
                    bound = 1;
                case 0:
                default:
                    result.add(PlatformType.GROUND);
                    currentLength++;
                    if (latency > 0) latency--;
                    break;
            }
            if (latency == 0) bound = 5;
        }
        GroundGenerationPattern newPattern = new GroundGenerationPattern(result);
        previousPattern = newPattern;
        return newPattern;
    }

    private List<PlatformType> createGap() {
        List<PlatformType> result = new ArrayList<>();
        result.add(PlatformType.LEFT_EDGE);
        result.add(PlatformType.GAP);
        result.add(PlatformType.GAP);
        result.add(PlatformType.RIGHT_EDGE);
        return result;
    }

    private List<PlatformType> createHighPlatformWithGap(int size) {
        List<PlatformType> result = new ArrayList<>();
        result.add(PlatformType.LEFT_EDGE);
        for (int i = 0; i < size; i++) {
            if (i == 0) result.add(PlatformType.LEFT_EDGE_HIGH);
            else if (i == size - 1) result.add(PlatformType.RIGHT_EDGE_HIGH);
            else result.add(PlatformType.HIGH);
        }
        result.add(PlatformType.RIGHT_EDGE);
        return result;
    }

    private List<PlatformType> createHighPlatformWithGround(int size) {
        List<PlatformType> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i == 0) result.add(PlatformType.LEFT_EDGE_HIGH_WITH_GROUND);
            else if (i == size - 1) result.add(PlatformType.RIGHT_EDGE_HIGH_WITH_GROUND);
            else result.add(PlatformType.HIGH_WITH_GROUND);
        }
        return result;
    }


    public static class GroundGenerationPattern {

        private final List<PlatformType> platformTypes;

        GroundGenerationPattern(List<PlatformType> types) {
            this.platformTypes = types;
        }

        public List<PlatformType> getPlatformTypes() {
            return platformTypes;
        }

    }
}

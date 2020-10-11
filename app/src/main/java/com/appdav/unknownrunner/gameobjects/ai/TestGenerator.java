package com.appdav.unknownrunner.gameobjects.ai;

public class TestGenerator extends GroundGenerator {

    @Override
    public GroundGenerationPattern nextPattern(int fromX, int blockWidth) {
        int count = (fromX + length) / blockWidth;
        PlatformType[] platformTypes = new PlatformType[count];
        PlatformType last;
        if (previousPattern != null && previousPattern.getLastType() == PlatformType.GAP){
            last = PlatformType.GAP;
        } else last = PlatformType.GROUND;
        int bound;
        for (int i = 0; i < count; i++){
            if (last == PlatformType.GAP) bound = 10;
            else bound = 8;
            PlatformType next = random.nextInt(bound) == 0 ? PlatformType.GAP : PlatformType.GROUND;
            platformTypes[i] = next;
            if (next == PlatformType.GAP && i > 0) platformTypes[i-1] = PlatformType.GAP;
            last = next;
        }
        if (count > 3) platformTypes[2] = PlatformType.HIGH;
        return new GroundGenerationPattern(platformTypes);
    }
}

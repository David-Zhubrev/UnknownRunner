package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.Random;

public class GroundGenerator {

    public enum PlatformType{
        GAP,
        GROUND,
        HIGH
    }

    protected GroundGenerationPattern previousPattern;
    protected final Random random;
    protected int length;

    public GroundGenerator() {
        this.random = Tools.random;
        length = Screen.screenWidth + 128;
    }

    public GroundGenerationPattern nextPattern(int fromX, int blockWidth){
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
            last = next;
        }
        return new GroundGenerationPattern(platformTypes);
    }


    public class GroundGenerationPattern{

        private final PlatformType[] platformTypes;
        private final int size;

        GroundGenerationPattern(PlatformType[] types){
            this.platformTypes = types;
            this.size = platformTypes.length;
        }

        public PlatformType[] getPlatformTypes() {
            return platformTypes;
        }

        public PlatformType getLastType(){
            return platformTypes[size-1];
        }
    }
}

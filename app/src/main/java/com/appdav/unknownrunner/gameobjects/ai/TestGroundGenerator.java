package com.appdav.unknownrunner.gameobjects.ai;

import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class TestGroundGenerator extends GroundGenerator {

    @Override
    public GroundGenerationPattern nextPattern(int fromX, int blockWidth) {
        int length = fromX;
        List<PlatformType> result = new ArrayList<>();
        while (length < Screen.screenWidth){
            result.add(PlatformType.GROUND);
            length += blockWidth;
        }
        return new GroundGenerationPattern(result);
    }
}

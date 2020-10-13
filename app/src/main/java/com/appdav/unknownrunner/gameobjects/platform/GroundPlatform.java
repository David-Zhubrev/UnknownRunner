package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.bitmapholders.PlatformBitmaps;

public class GroundPlatform extends Platform {

    public GroundPlatform(Resources res, Speed speed) {
        super(res, speed);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(PlatformBitmaps.getGroundEdgeFrames(getResources()), null);
    }
}

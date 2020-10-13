package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.bitmapholders.PlatformBitmaps;

public class GapPlatform extends Platform {

    public GapPlatform(Resources res, Speed speed) {
        super(res, speed);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(PlatformBitmaps.getGapFrames(getResources()), null);
    }

    @Override
    public Rect getCollisionRect() {
        return null;
    }
}

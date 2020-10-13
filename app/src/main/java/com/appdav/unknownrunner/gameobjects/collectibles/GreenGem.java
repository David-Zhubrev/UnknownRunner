package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.bitmapholders.GemBitmaps;

public class GreenGem extends Collectible {

    public GreenGem(Resources res, Speed levelSpeed) {
        super(res, 10, levelSpeed);
    }


    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(GemBitmaps.getGreenGemFrames(getResources()), null);
    }

    @Override
    public int getPrice() {
        return 500;
    }
}

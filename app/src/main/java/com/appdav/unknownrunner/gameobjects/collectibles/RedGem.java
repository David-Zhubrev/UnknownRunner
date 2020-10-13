package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.bitmapholders.GemBitmaps;

public class RedGem extends Collectible {

    public RedGem(Resources res, Speed levelSpeed) {
        super(res, 10, levelSpeed);
    }

    @Override
    public int getPrice() {
        return 100;
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(GemBitmaps.getRedGemFrames(getResources()), null);


    }
}

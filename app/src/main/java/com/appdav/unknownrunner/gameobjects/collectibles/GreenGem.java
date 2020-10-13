package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.collectibles.Collectible;

public class GreenGem extends Collectible {

    private FrameManager mainFrameManager;

    public GreenGem(Resources res, Speed levelSpeed) {
        super(res, 10, levelSpeed);
    }


    @Override
    protected FrameManager createMainFrameManager() {
        if (mainFrameManager != null) return mainFrameManager;
        else {
            mainFrameManager = createFrameManager(R.drawable.green_gem);
            return mainFrameManager;
        }

    }

    @Override
    public int getPrice() {
        return 500;
    }
}

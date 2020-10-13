package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;

public class BlueGem extends Collectible {

    private static FrameManager mainFrameManager;

    public BlueGem(Resources res, Speed levelSpeed) {
        super(res, 10, levelSpeed);
    }

    @Override
    public int getPrice() {
        return 200;
    }

    @Override
    protected FrameManager createMainFrameManager() {
        if (mainFrameManager != null) return mainFrameManager;
        else {
            mainFrameManager = createFrameManager(R.drawable.blue_gem);
            return mainFrameManager;
        }
    }
}

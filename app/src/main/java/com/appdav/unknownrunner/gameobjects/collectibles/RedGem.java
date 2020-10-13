package com.appdav.unknownrunner.gameobjects.collectibles;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;

public class RedGem extends Collectible {

    private static FrameManager mainFrameManager;

    public RedGem(Resources res, Speed levelSpeed) {
        super(res, 10, levelSpeed);
    }

    @Override
    public int getPrice() {
        return 100;
    }

    @Override
    protected FrameManager createMainFrameManager() {
        if (mainFrameManager != null) return mainFrameManager;
        else {
            mainFrameManager = createFrameManager(R.drawable.red_gem);
            return mainFrameManager;
        }

    }
}

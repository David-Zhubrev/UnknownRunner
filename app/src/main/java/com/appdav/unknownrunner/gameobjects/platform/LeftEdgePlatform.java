package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;

public class LeftEdgePlatform extends Platform {

    public LeftEdgePlatform(Resources res, Speed speed) {
        super(res, speed);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(R.drawable.tile_left_edge);
    }
}

package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;

public class GroundPlatform extends Platform {

    public GroundPlatform(Resources res, Speed speed) {
        super(res, speed);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(R.drawable.tile28);
    }
}

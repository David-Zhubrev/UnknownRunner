package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;

public class RightEdgePlatform extends Platform{


    public RightEdgePlatform(Resources res, Speed speed) {
        super(res, speed);
    }


    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(R.drawable.tile_right_edge);
    }
}

package com.appdav.unknownrunner.gameobjects.platform;

import android.content.res.Resources;
import android.graphics.Rect;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;

public class GapPlatform extends Platform {

    public GapPlatform(Resources res, Speed speed) {
        super(res, speed);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        return createFrameManager(R.drawable.gap);
    }

    @Override
    public Rect getCollisionRect() {
        return null;
    }
}

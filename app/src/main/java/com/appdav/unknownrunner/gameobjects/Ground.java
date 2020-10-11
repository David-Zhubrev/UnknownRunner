package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator.GroundGenerationPattern;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator.PlatformType;
import com.appdav.unknownrunner.gameobjects.ai.TestGenerator;
import com.appdav.unknownrunner.gameobjects.platform.GapPlatform;
import com.appdav.unknownrunner.gameobjects.platform.GroundPlatform;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class Ground implements GameDrawable {

    private List<Platform> platforms;
    private Resources res;
    private final Speed speed;

    private boolean isDestroyed = false;

    private int platformWidth, platformHeight;

    private GroundGenerator generator;

    public Ground(Resources res, Speed speed) {
        this.res = res;
        this.speed = speed;
        //TODO change generator
        generator = new TestGenerator();
        platforms = new ArrayList<>();
        platforms.addAll(createFullScreenPlatform(0));
    }

    private Platform createPlatform(PlatformType type) {
        Platform result;
        switch (type) {
            case GAP:
                result = new GapPlatform(res, speed);
                result.y = Screen.screenHeight - result.height;
                break;
            case GROUND:
                result = new GroundPlatform(res, speed);
                result.y = Screen.screenHeight - result.height;
                break;
            case HIGH:
                result = new GroundPlatform(res, speed);
                result.y = Screen.screenHeight - result.height * 2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return result;
    }


    private List<Platform> createFullScreenPlatform(int startingCoordinate) {
        if (platformWidth == 0) {
            Platform platform = createPlatform(PlatformType.GAP);
            platformWidth = platform.width;
            platformHeight = platform.height;
            platform.destroy();
        }
        GroundGenerationPattern pattern = generator.nextPattern(startingCoordinate, platformWidth);
        List<Platform> platforms = new ArrayList<>();
        int x = startingCoordinate;
        for (PlatformType type : pattern.getPlatformTypes()) {
            Platform platform = createPlatform(type);
            platform.x = x;
            x += platform.width;
            platforms.add(platform);
        }
        return platforms;
    }

    @Override
    public void drawObject(Canvas canvas, Paint paint) {
        for (GameDrawable drawable : platforms) {
            drawable.drawObject(canvas, paint);
        }
    }

    public List<Collidable> getCollidables() {
        List<Collidable> list = new ArrayList<>();
        for (Platform p : platforms) {
            if (p.x > -Screen.screenWidth / 5 && p.x < Screen.screenWidth * 1.2)
                list.add(p);
        }
        return list;
    }

    @Override
    public void update() {
        int lastIndex = platforms.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            Platform platform = platforms.get(i);
            if (platform.isDestroyed) {
                platforms.remove(platform);
                lastIndex--;
            } else {
                platform.update();
            }
        }
        Platform lastPlatform = platforms.get(lastIndex);
        if (lastPlatform.x < Screen.screenWidth + lastPlatform.width) {
            platforms.addAll(createFullScreenPlatform(lastPlatform.x + lastPlatform.width));
        }
    }

    @Override
    public void destroy() {
        platforms = null;
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }


}

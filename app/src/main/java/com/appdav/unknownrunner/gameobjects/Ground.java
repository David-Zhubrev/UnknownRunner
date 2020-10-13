package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator.GroundGenerationPattern;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator.PlatformType;
import com.appdav.unknownrunner.gameobjects.platform.GapPlatform;
import com.appdav.unknownrunner.gameobjects.platform.GroundPlatform;
import com.appdav.unknownrunner.gameobjects.platform.LeftEdgePlatform;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.gameobjects.platform.RightEdgePlatform;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class Ground implements GameDrawable {

    private List<Platform> platforms;
    private Resources res;
    private final Speed speed;

    private boolean isDestroyed = false;

    private int platformWidth, platformHeight;

    private int ground_level_low;
    private int ground_level_high;

    private GroundGenerator generator;

    public Ground(Resources res, Speed speed) {
        this.res = res;
        this.speed = speed;
        generator = new GroundGenerator();
        platforms = new ArrayList<>();
        platforms.addAll(createFullScreenPlatform(0));
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    private List<Platform> platformBuffer;

    private List<Platform> createPlatform(PlatformType type) {
        if (platformBuffer == null) platformBuffer = new ArrayList<>();
        else platformBuffer.clear();
        Platform result;
        switch (type) {
            case GAP:
                result = new GapPlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case GROUND:
                result = new GroundPlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case HIGH:
                result = new GroundPlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                break;
            case HIGH_WITH_GROUND:
                result = new GroundPlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                result = new GroundPlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case RIGHT_EDGE:
                result = new RightEdgePlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case LEFT_EDGE:
                result = new LeftEdgePlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case RIGHT_EDGE_HIGH:
                result = new LeftEdgePlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                break;
            case LEFT_EDGE_HIGH:
                result = new RightEdgePlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                break;
            case RIGHT_EDGE_HIGH_WITH_GROUND:
                result = new LeftEdgePlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                result = new GroundPlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            case LEFT_EDGE_HIGH_WITH_GROUND:
                result = new RightEdgePlatform(res, speed);
                result.y = ground_level_high;
                platformBuffer.add(result);
                result = new GroundPlatform(res, speed);
                result.y = ground_level_low;
                platformBuffer.add(result);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return platformBuffer;
    }


    private List<Platform> createFullScreenPlatform(int startingCoordinate) {
        if (platformWidth == 0) {
            Platform platform = new GroundPlatform(res, speed);
            platformWidth = platform.width;
            platformHeight = platform.height;
            ground_level_low = Screen.screenHeight - platformHeight;
            ground_level_high = Screen.screenHeight - platformHeight * 3;
            platform.destroy();
        }
        GroundGenerationPattern pattern = generator.nextPattern(startingCoordinate, platformWidth);
        List<Platform> platforms = new ArrayList<>();
        int x = startingCoordinate;
        for (PlatformType type : pattern.getPlatformTypes()) {
            List<Platform> nextPlatforms = createPlatform(type);
            int arraySize = nextPlatforms.size();
            for (int i = 0; i < arraySize; i++) {
                Platform platform = nextPlatforms.get(i);
                platform.x = x;
                platforms.add(platform);
                if (i == arraySize - 1) {
                    x += platform.width;
                }
            }
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

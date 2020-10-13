package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.collectibles.BlueGem;
import com.appdav.unknownrunner.gameobjects.collectibles.Collectible;
import com.appdav.unknownrunner.gameobjects.collectibles.GreenGem;
import com.appdav.unknownrunner.gameobjects.collectibles.RedGem;
import com.appdav.unknownrunner.gameobjects.platform.GapPlatform;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectibleGenerator {

    private final Random random;
    private final Resources res;
    private final Speed speed;

    CollectibleGenerator(Resources res, Speed speed) {
        this.random = Tools.random;
        this.speed = speed;
        this.res = res;
    }

    public Collectible nextCollectible() {
        Collectible c;
        switch (random.nextInt(5)) {
            case 0:
            case 1:
                c = new RedGem(res, speed);
                break;
            case 2:
            case 3:
                c = new BlueGem(res, speed);
                break;
            default:
                c = new GreenGem(res, speed);
                break;
        }
        c.x = Screen.screenWidth;
        c.y = random.nextInt(Screen.screenHeight - Tools.blockHeight);
        return c;
    }
}




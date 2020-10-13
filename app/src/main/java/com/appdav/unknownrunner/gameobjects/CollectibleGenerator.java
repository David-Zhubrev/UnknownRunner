package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.collectibles.BlueGem;
import com.appdav.unknownrunner.gameobjects.collectibles.Collectible;
import com.appdav.unknownrunner.gameobjects.collectibles.GreenGem;
import com.appdav.unknownrunner.gameobjects.collectibles.RedGem;
import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

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
        int initialY = Screen.screenHeight / 3 + random.nextInt(Screen.screenHeight / 3);
        Collectible c = chooseCollectible();
        c.x = Screen.screenWidth;
        c.y = initialY;
        return c;
    }

    private Collectible chooseCollectible() {
        switch (random.nextInt(6)) {
            case 0:
            case 1:
            case 2:
                return new RedGem(res, speed);
            case 3:
            case 4:
                return new BlueGem(res, speed);
            case 5:
            default:
                return new GreenGem(res, speed);
        }
    }


}




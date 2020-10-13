package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.characters.Enemy;
import com.appdav.unknownrunner.gameobjects.characters.Golem;
import com.appdav.unknownrunner.gameobjects.platform.GapPlatform;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator {

    private final Random random;
    private final Resources res;
    private final Speed speed;

    EnemyGenerator(Resources res, Speed speed) {
        this.random = Tools.random;
        this.speed = speed;
        this.res = res;
    }

    public Enemy nextEnemy(List<Platform> platformList) {
        List<Platform> conformingPlatforms = new ArrayList<>();
        for (Platform p : platformList) {
            if (p.x > Screen.screenWidth && !(p instanceof GapPlatform)) {
                conformingPlatforms.add(p);
            }
        }
        if (!conformingPlatforms.isEmpty()) {
            Enemy result = new Golem(res, speed);
            Platform p = conformingPlatforms.get(random.nextInt(conformingPlatforms.size()));
            result.x = p.x + p.width / 2;
            result.y = p.y - result.height - 5;
            return result;
        } else {
            Enemy result = new Golem(res, speed);
            result.x = Screen.screenWidth;
            result.y = Screen.screenHeight / 2;
            return result;
        }
    }


}

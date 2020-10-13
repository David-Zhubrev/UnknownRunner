package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.gameobjects.characters.Golem;
import com.appdav.unknownrunner.gameobjects.characters.MainCharacter;
import com.appdav.unknownrunner.gameobjects.collectibles.Collectible;
import com.appdav.unknownrunner.tools.Screen;
import com.appdav.unknownrunner.tools.Tools;

import java.util.ArrayList;
import java.util.List;

public class MountainLevel extends Level {

    private static Speed speed = new Speed(15);
    private CollectibleGenerator collectibleGenerator;

    public MountainLevel(Resources res, UiGameplayCallback listener) {
        super(res, speed, listener);
        speed = new Speed(15);

    }

    @Override
    protected void initializeObjects() {
        this.collidables = new ArrayList<>();
        this.drawables = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.collectibles = new ArrayList<>();

        this.background = new MountainBackground(res);
        drawables.add(background);

        this.ground = new Ground(res, speed);
        drawables.add(ground);

        MainCharacter character = new MainCharacter(res, speed, this);
        this.player = new HumanPlayer(character);
        character.attachPlayer(player);
        drawables.add(character);
        collidables.add(character);

        this.collectibleGenerator = new CollectibleGenerator(res, speed);
    }

    private void addGolem() {
        Golem golem = new Golem(res, speed);
        golem.x = Screen.screenWidth;
        golem.y = Screen.screenHeight / 2;

        collidables.add(golem);
        enemies.add(golem);
        drawables.add(golem);
    }

    private void addCollectible() {
        Collectible c = collectibleGenerator.nextCollectible();
        collectibles.add(c);
        collidables.add(c);
        drawables.add(c);
    }

    private void checkGolems() {
        if (!isDestroyed() && enemies.isEmpty()) {
            addGolem();
        }
    }

    private void checkCollectibles() {
        if (Tools.random.nextInt(30) == 12) {
            addCollectible();
        }
    }

    @Override
    public void update() {
        checkCollectibles();
        super.update();
        checkGolems();
    }
}

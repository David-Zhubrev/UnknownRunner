package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.ai.GroundGenerator;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.gameobjects.characters.Golem;
import com.appdav.unknownrunner.gameobjects.characters.MainCharacter;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;

public class MountainLevel extends Level {

    private static Speed speed = new Speed(15);

    public MountainLevel(Resources res, StopThreadListener listener) {
        super(res, speed, listener);
        speed = new Speed(15);

    }

    @Override
    protected void initializeObjects() {
        this.collidables = new ArrayList<>();
        this.drawables = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.background = new MountainBackground(res);
        drawables.add(background);

        this.ground = new Ground(res, speed);
        drawables.add(ground);

        MainCharacter character = new MainCharacter(res, speed, this);
        this.player = new HumanPlayer(character);
        character.attachPlayer(player);
        drawables.add(character);
        collidables.add(character);

    }

    private void addGolem() {
        Golem golem = new Golem(res, speed);
        golem.x = Screen.screenWidth;
        golem.y = Screen.screenHeight / 2;

        collidables.add(golem);
        enemies.add(golem);
        drawables.add(golem);
    }

    private void checkGolems() {
        if (!isDestroyed() && enemies.isEmpty()) {
            addGolem();
        }
    }

    @Override
    public void update() {
        super.update();
        checkGolems();
    }
}

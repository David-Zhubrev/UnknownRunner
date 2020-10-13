package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;

import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.ai.GolemAi;
import com.appdav.unknownrunner.gameobjects.bitmapholders.GolemBitmaps;
import com.appdav.unknownrunner.tools.CollisionHandler;

public class Golem extends Enemy implements GameObject.Callback {

    private FrameManager fallFrameManager;
    private FrameManager walkFrameManager;
    private FrameManager dieFrameManager;
    private FrameManager winFrameManager;

    private static final int GOLEM_DOWNSCALE = 10;
    private static final int GOLEM_SPEED = 10;

    public Golem(Resources res, Speed speed) {
        super(res, speed, GOLEM_DOWNSCALE);
        this.thresholdTop = thresholdBottom = height / 5;
        this.thresholdLeft = thresholdRight = width / 10;
        extraSpeed = GOLEM_SPEED;
        attachPlayer(new GolemAi(this));
        createSecondaryFrameManagers();
    }


    private void createSecondaryFrameManagers() {
        fallFrameManager = createFrameManager(GolemBitmaps.Bitmaps.getFallingFrames(getResources()), null);
        dieFrameManager = createFrameManager(GolemBitmaps.Bitmaps.getDyingFrames(getResources()), this);
        winFrameManager = createFrameManager(GolemBitmaps.Bitmaps.getIdleFrames(getResources()), null);
    }

    @Override
    protected FrameManager createMainFrameManager() {
        walkFrameManager = createFrameManager(GolemBitmaps.Bitmaps.getWakingFrames(getResources()), null);
        return walkFrameManager;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (dieFrameManager.hasCallback())
            dieFrameManager.detachCallback();
    }

    @Override
    void onUpdateBeforeCollisionHandling() {
        if (collisions != null && !collisions.isEmpty()) {
            for (Collision collision : collisions) {
                if (collision.source instanceof MainCharacter && collision.position == CollisionHandler.Position.TOP) {
                    die();
                }
            }
        }
    }

    @Override
    public void win() {
        currentFrameManager = winFrameManager;
    }

    @Override
    public void die() {
        if (isDead) return;
        isDead = true;
        currentFrameManager = dieFrameManager;
        collisions = null;
        nextMoves = null;
        this.speed = Speed.zero;
        if (player != null) {
            player.releasePlayer();
            detachPlayer();
        }
    }

    @Override
    void onUpdateAfterCollisionHandling() {
        if (isDead) return;
        if (nextMoves != null && nextMoves.contains(Move.FALL))
            currentFrameManager = fallFrameManager;
        else currentFrameManager = walkFrameManager;
    }

    @Override
    void onUpdateAfterMovementHandling() {
    }

    @Override
    public void onLastFrameShown(FrameManager manager) {
        if (manager == dieFrameManager) {
            this.destroy();
        }
    }
}

package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.gameobjects.ai.GolemAi;
import com.appdav.unknownrunner.tools.CollisionHandler;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Enemy implements GameObject.Callback {

    private static FrameManager fallFrameManager;
    private static FrameManager walkFrameManager;
    private static FrameManager dieFrameManager;
    private static FrameManager winFrameManager;

    private static final int GOLEM_DOWNSCALE = 8;
    private static final int GOLEM_SPEED = 10;

    public Golem(Resources res, Speed speed) {
        super(res, speed, GOLEM_DOWNSCALE);
        this.thresholdTop = thresholdBottom = height / 6;
        this.thresholdLeft = thresholdRight = width / 6;
        extraSpeed = GOLEM_SPEED;
        attachPlayer(new GolemAi(this));
        createSecondaryFrameManagers();
        attachCallback();
    }

    private void attachCallback() {
        if (!dieFrameManager.hasCallback()) {
            dieFrameManager.attachCallback(this);
        }
    }

    private void createSecondaryFrameManagers() {
        if (fallFrameManager == null)
            fallFrameManager = createFallFrameManager();
        if (dieFrameManager == null)
            dieFrameManager = createDieFrameManager();
        if (winFrameManager == null)
            winFrameManager = createWinFrameManager();
    }

    @Override
    protected FrameManager createMainFrameManager() {
        if (walkFrameManager != null) {
            return walkFrameManager;
        }
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.golem1_running_000);
        resIds.add(R.drawable.golem1_running_001);
        resIds.add(R.drawable.golem1_running_002);
        resIds.add(R.drawable.golem1_running_003);
        resIds.add(R.drawable.golem1_running_004);
        resIds.add(R.drawable.golem1_running_005);
        resIds.add(R.drawable.golem1_running_006);
        resIds.add(R.drawable.golem1_running_007);
        resIds.add(R.drawable.golem1_running_008);
        resIds.add(R.drawable.golem1_running_009);
        resIds.add(R.drawable.golem1_running_010);
        resIds.add(R.drawable.golem1_running_011);
        walkFrameManager = createFrameManager(resIds, null, true);
        return walkFrameManager;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (dieFrameManager.hasCallback()) {
            dieFrameManager.detachCallback();
        }
    }

    private FrameManager createWinFrameManager() {
        return null;
        //return currentFrameManager;
    }

    private FrameManager createDieFrameManager() {
        if (dieFrameManager != null) return dieFrameManager;
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.golem1_dying_000);
        resIds.add(R.drawable.golem1_dying_001);
        resIds.add(R.drawable.golem1_dying_002);
        resIds.add(R.drawable.golem1_dying_003);
        resIds.add(R.drawable.golem1_dying_004);
        resIds.add(R.drawable.golem1_dying_005);
        resIds.add(R.drawable.golem1_dying_006);
        resIds.add(R.drawable.golem1_dying_007);
        resIds.add(R.drawable.golem1_dying_008);
        resIds.add(R.drawable.golem1_dying_009);
        resIds.add(R.drawable.golem1_dying_010);
        resIds.add(R.drawable.golem1_dying_011);
        resIds.add(R.drawable.golem1_dying_012);
        resIds.add(R.drawable.golem1_dying_013);
        resIds.add(R.drawable.golem1_dying_014);
        return createFrameManager(resIds, this, true);
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
        isAlive = false;
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
        if (!isAlive) return;
        if (nextMoves != null && nextMoves.contains(Move.FALL))
            currentFrameManager = fallFrameManager;
        else currentFrameManager = walkFrameManager;
    }

    @Override
    void onUpdateAfterMovementHandling() {
    }

    private FrameManager createFallFrameManager() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.golem1_falling_down_000);
        resIds.add(R.drawable.golem1_falling_down_001);
        resIds.add(R.drawable.golem1_falling_down_002);
        resIds.add(R.drawable.golem1_falling_down_003);
        resIds.add(R.drawable.golem1_falling_down_004);
        resIds.add(R.drawable.golem1_falling_down_005);
        fallFrameManager = createFrameManager(resIds, null, true);
        return fallFrameManager;
    }

    @Override
    public void onLastFrameShown(FrameManager manager) {
        if (manager == dieFrameManager) {
            this.destroy();
        }
    }
}

package com.appdav.unknownrunner.gameobjects.characters;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;
import com.appdav.unknownrunner.Speed;
import com.appdav.unknownrunner.gameobjects.Collision;
import com.appdav.unknownrunner.gameobjects.GameObject;
import com.appdav.unknownrunner.gameobjects.Player;
import com.appdav.unknownrunner.gameobjects.ai.HumanPlayer;
import com.appdav.unknownrunner.gameobjects.platform.Platform;
import com.appdav.unknownrunner.tools.CollisionHandler;
import com.appdav.unknownrunner.gameobjects.Move;
import com.appdav.unknownrunner.tools.Screen;

import java.util.ArrayList;
import java.util.List;

public class MainCharacter extends Character implements GameObject.Callback {

    private static FrameManager walkFrameManager;
    private static FrameManager castingFrameManager;
    private static FrameManager dyingFrameManager;

    private static final int JUMP_HEIGHT = 300;
    private static final int initialPosition = Screen.screenWidth / 3;


    private HumanPlayer player;

    public MainCharacter(Resources res, Speed speed) {
        super(res, speed, 6);
        createSecondaryFrameManagers();
        this.extraSpeed = -speed.speed / 2;
        thresholdTop = thresholdBottom = height / 4;
        thresholdLeft = thresholdRight = width / 4;
    }

    @Override
    public void attachPlayer(Player player) {
        super.attachPlayer(player);
        if (player instanceof HumanPlayer) {
            this.player = (HumanPlayer) player;
        }
    }

    private void die() {
        currentFrameManager = dyingFrameManager;
        collisions = null;
        nextMoves = null;
        player.die();
    }

    @Override
    void onUpdateBeforeCollisionHandling() {
        if (x < -Screen.screenWidth / 2 || y + height / 2 >  Screen.screenHeight) {
            die();
            return;
        } else if (x < initialPosition) {
            nextMoves.add(Move.MOVE_RIGHT);
        }
        if (isJumping) nextMoves.add(Move.JUMP);
        if (collisions != null && !collisions.isEmpty()) {
            for (Collision collision : collisions) {
                if (collision.source instanceof Enemy) {
                    if (collision.position == CollisionHandler.Position.BOTTOM) {
                        ((Enemy) collision.source).die();
                        nextMoves.add(Move.JUMP);
                    } else {
                        die();
                    }
                }
            }
        }
    }

    @Override
    public void onCollisionWith(Collision collision) {
        super.onCollisionWith(collision);
    }

    @Override
    void onUpdateAfterCollisionHandling() {
        if (nextMoves == null) return;
        if (isJumping) {
            nextMoves.remove(Move.FALL);
        }
        if (nextMoves.contains(Move.JUMP)) {
            if (y <= 0) {
                isJumping = false;
                currentVerticalSpeed = 0;
            } else {
                if (!isJumping) {
                    isJumping = true;
                    currentVerticalSpeed = -70;
                }
                JumpInterpolator.nextMove(this);
            }
        }
    }

    @Override
    void onUpdateAfterMovementHandling() {
        if (y + height < 0) die();
    }

    private void createSecondaryFrameManagers() {
        if (castingFrameManager == null) {
            castingFrameManager = createCastingFrameManager();
        }
        if (dyingFrameManager == null) {
            dyingFrameManager = createDyingFrameManager();
        }
    }

    @Override
    protected FrameManager createMainFrameManager() {
        if (walkFrameManager != null) return walkFrameManager;
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.wraith_03_moving_forward_000);
        resIds.add(R.drawable.wraith_03_moving_forward_001);
        resIds.add(R.drawable.wraith_03_moving_forward_002);
        resIds.add(R.drawable.wraith_03_moving_forward_003);
        resIds.add(R.drawable.wraith_03_moving_forward_004);
        resIds.add(R.drawable.wraith_03_moving_forward_005);
        resIds.add(R.drawable.wraith_03_moving_forward_006);
        resIds.add(R.drawable.wraith_03_moving_forward_007);
        resIds.add(R.drawable.wraith_03_moving_forward_008);
        resIds.add(R.drawable.wraith_03_moving_forward_009);
        resIds.add(R.drawable.wraith_03_moving_forward_010);
        resIds.add(R.drawable.wraith_03_moving_forward_011);
        walkFrameManager = createFrameManager(resIds, null);
        return walkFrameManager;
    }

    private FrameManager createCastingFrameManager() {
        if (castingFrameManager != null) return castingFrameManager;
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.wraith_03_casting_spells_000);
        resIds.add(R.drawable.wraith_03_casting_spells_001);
        resIds.add(R.drawable.wraith_03_casting_spells_002);
        resIds.add(R.drawable.wraith_03_casting_spells_003);
        resIds.add(R.drawable.wraith_03_casting_spells_004);
        resIds.add(R.drawable.wraith_03_casting_spells_005);
        resIds.add(R.drawable.wraith_03_casting_spells_006);
        resIds.add(R.drawable.wraith_03_casting_spells_007);
        resIds.add(R.drawable.wraith_03_casting_spells_008);
        resIds.add(R.drawable.wraith_03_casting_spells_009);
        resIds.add(R.drawable.wraith_03_casting_spells_010);
        resIds.add(R.drawable.wraith_03_casting_spells_011);
        resIds.add(R.drawable.wraith_03_casting_spells_012);
        resIds.add(R.drawable.wraith_03_casting_spells_013);
        resIds.add(R.drawable.wraith_03_casting_spells_014);
        resIds.add(R.drawable.wraith_03_casting_spells_015);
        resIds.add(R.drawable.wraith_03_casting_spells_016);
        resIds.add(R.drawable.wraith_03_casting_spells_017);
        return createFrameManager(resIds, this);
    }

    private FrameManager createDyingFrameManager() {
        List<Integer> resIds = new ArrayList<>();
        resIds.add(R.drawable.wraith_03_dying_000);
        resIds.add(R.drawable.wraith_03_dying_001);
        resIds.add(R.drawable.wraith_03_dying_002);
        resIds.add(R.drawable.wraith_03_dying_003);
        resIds.add(R.drawable.wraith_03_dying_004);
        resIds.add(R.drawable.wraith_03_dying_005);
        resIds.add(R.drawable.wraith_03_dying_006);
        resIds.add(R.drawable.wraith_03_dying_007);
        resIds.add(R.drawable.wraith_03_dying_008);
        resIds.add(R.drawable.wraith_03_dying_009);
        resIds.add(R.drawable.wraith_03_dying_010);
        resIds.add(R.drawable.wraith_03_dying_011);
        resIds.add(R.drawable.wraith_03_dying_012);
        resIds.add(R.drawable.wraith_03_dying_013);
        resIds.add(R.drawable.wraith_03_dying_014);
        return createFrameManager(resIds, this);
    }

    @Override
    public void onLastFrameShown(FrameManager manager) {
        if (manager == dyingFrameManager) {
            player.endGame();
            player = null;
            destroy();
        }
        //TODO
    }
}

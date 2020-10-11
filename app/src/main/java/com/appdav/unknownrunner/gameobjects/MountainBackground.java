package com.appdav.unknownrunner.gameobjects;

import android.content.res.Resources;

import com.appdav.unknownrunner.R;


import java.util.ArrayList;
import java.util.List;

public class MountainBackground extends Background {

    protected MountainBackground(Resources res) {
        super(res, createLayers(res));
    }

    private static List<Layer> createLayers(Resources res) {
        List<Layer> layers = new ArrayList<>();
        layers.add(new Layer(res, R.drawable.parallax_mountain_bg, 0, false));
        layers.add(new Layer(res, R.drawable.parallax_mountain_montain_far, 1, false));
        layers.add(new Layer(res, R.drawable.parallax_mountain_mountains, 3, false));
        //layers.add(new Layer(res, R.drawable.parallax_mountain_trees, 8, false));
        //layers.add(new Layer(res, R.drawable.parallax_mountain_foreground_trees, 10, false));
        return layers;
    }

}

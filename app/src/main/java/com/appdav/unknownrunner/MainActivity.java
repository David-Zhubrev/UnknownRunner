package com.appdav.unknownrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.appdav.unknownrunner.tools.Screen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting up Screen util class for screen optimization
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Screen.setup(point);

        setContentView(R.layout.activity_main);
        findViewById(R.id.tvStart).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
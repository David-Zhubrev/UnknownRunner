package com.appdav.unknownrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.appdav.unknownrunner.tools.Preferences;
import com.appdav.unknownrunner.tools.Score;
import com.appdav.unknownrunner.tools.Screen;

public class MainActivity extends AppCompatActivity {

    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideSystemUi();
        super.onCreate(savedInstanceState);
        Preferences.setupPreferences(getSharedPreferences(Preferences.PREF_NAME, MODE_PRIVATE));
        Score.highScore = Preferences.getHighScore();
        //Setting up Screen util class for screen optimization
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Screen.setup(point);
        setContentView(R.layout.activity_main);
        score = findViewById(R.id.highScoreMain);
        findViewById(R.id.tvStart).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        });
    }


    private void hideSystemUi() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String highScore = "High score:\n" + Score.highScore;
        score.setText(highScore);
    }
}
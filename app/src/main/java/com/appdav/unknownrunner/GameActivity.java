package com.appdav.unknownrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdav.unknownrunner.tools.Score;

public class GameActivity extends AppCompatActivity implements Dialogs.DialogCallback, GameView.GameActivityCallback {

    private GameView gameView;
    private ImageView ivPause;
    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUi();
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.gameView);
        ivPause = findViewById(R.id.ivPause);
        ivPause.setOnClickListener(v -> {
            if (gameView.isRunning()) {
                gameView.stopThread();
                ivPause.setVisibility(View.INVISIBLE);
                new Dialogs.PauseDialog(GameActivity.this).show(getSupportFragmentManager(), null);
            }
        });
        tvScore = findViewById(R.id.tvScore);
        tvScore.setText(createScoreText());
        gameView.attachCallback(this);
    }

    private String createScoreText() {
        return "Score: " + Score.score;
    }

    private void hideSystemUi() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            hideSystemUi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameView.detachCallback();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvResume:
                gameView.startThread();
                break;
            case R.id.tvGameOverToMainMenu:
            case R.id.tvPauseToMainMenu:
                gameView.destroy();
                finish();
                break;
            case R.id.tvRestart:
                gameView.restart();
                break;
        }
        hideSystemUi();
    }

    @Override
    public void onGameOverScreenShow() {
        new Dialogs.GameOverDialog(this).show(getSupportFragmentManager(), null);
        ivPause.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGameStart() {
        ivPause.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGamePaused() {
        ivPause.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onScoreUpdated() {
        runOnUiThread(() -> tvScore.setText(createScoreText()));
    }
}
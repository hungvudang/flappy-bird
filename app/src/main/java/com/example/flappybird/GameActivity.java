package com.example.flappybird;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class GameActivity extends Activity {
    private View gameView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(GameActivity.this);
        setContentView(gameView);
    }
}

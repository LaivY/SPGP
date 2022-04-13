package com.laivy.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.laivy.dragonflight.R;
import com.laivy.dragonflight.framework.GameView;
import com.laivy.dragonflight.game.MainGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        GameView.view.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (GameView.view != null)
            GameView.view.resumeGame();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        MainGame.clear();
        super.onDestroy();
    }
}
package com.laivy.the.shape.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.laivy.the.shape.R;
import com.laivy.the.shape.game.GameScene;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        setContentView(R.layout.activity_main);
    }

    public void onPlayButtonClick(View view) {
        setContentView(R.layout.activity_game);
    }

    public void onExitButtonClick(View view) {
        finish();
    }
}

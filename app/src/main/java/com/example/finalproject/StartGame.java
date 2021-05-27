package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class StartGame extends Activity {
    GameView gameview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameview = new GameView(this);
        setContentView(gameview);
    }
}

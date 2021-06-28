package com.example.vcimpanimalssound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnPlayCat;
    Button btnPlayLion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnPlayCat = findViewById(R.id.btnPlayCat);
        btnPlayLion = findViewById(R.id.btnPlayLion);

        setListeners();
    }

    private void setListeners() {
        btnPlayCat.setOnClickListener(v_ -> {
            playSound(R.raw.gato);
        });
        btnPlayLion.setOnClickListener(v_ -> {
            playSound(R.raw.leon);
        });
    }

    private void playSound(int resId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
    }
}
package com.example.vcimpmediaplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_PICK_SOUNDFILE = 1;
    private MediaPlayer mediaPlayer;
    private int lastCurrentPositon;
    private boolean cycle;
    private Uri fileUri;
    private String url;
    private boolean playURL;
    TextView txtURL;
    Button btnSearchFile;
    Button btnSearchURL;
    Button btnPlay;
    Button btnStop;
    Button btnCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnSearchFile = findViewById(R.id.btnSearchFile);
        btnSearchURL = findViewById(R.id.btnSearchURL);
        txtURL = findViewById(R.id.txtURL);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        btnCycle = findViewById(R.id.btnCycle);

        setListeners();
    }

    private void setListeners() {
        btnSearchFile.setOnClickListener(v -> {
            searchFile();
        });
        btnSearchURL.setOnClickListener(v -> {
            searchURL();
        });
        btnPlay.setOnClickListener(v -> {
            playPauseMusic();
        });
        btnStop.setOnClickListener(v -> {
            stopMusic();
        });
        btnCycle.setOnClickListener(v -> {
            updateCycleButton();
        });
    }

    private void searchFile() {
        txtURL.setEnabled(false);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(intent, REQ_CODE_PICK_SOUNDFILE);

    }

    private void searchURL() {
        txtURL.setEnabled(true);
        url = txtURL.getText().toString();
        playURL = !url.isEmpty();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_PICK_SOUNDFILE:
                if (resultCode == RESULT_OK) {
                    fileUri = data.getData();
                    lastCurrentPositon = 0;
                    playURL = false;
                }
                break;
        }
    }

    private void playPauseMusic() {
        boolean success;
        if (playMusic()) {
            success = startSong();
        } else {
            success = pauseSong();
        }
        if (success) {
            updatePlayButton();
        }
    }

    private boolean startSong() {
        if (lastCurrentPositon != 0) {
            mediaPlayer.seekTo(lastCurrentPositon);
        } else {
            if (playURL) {
                try {
                    mediaPlayer= new MediaPlayer();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                if (fileUri == null) {
                    return false;
                }
                mediaPlayer = MediaPlayer.create(this, fileUri);
            }
        }
        mediaPlayer.start();
        cycle();
        return true;
    }

    private boolean pauseSong() {
        lastCurrentPositon = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();

        return true;
    }

    private void stopMusic() {
        if (isPlaying()) {
            mediaPlayer.stop();
            updatePlayButton();
            lastCurrentPositon = 0;
        }
    }

    private boolean playMusic() {
        return btnPlay.getText().toString().equals("Play");
    }

    private boolean cycleMusic() {
        return btnCycle.getText().toString().equals("Cycle");
    }

    private boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.isPlaying();
    }

    private void updatePlayButton() {
        if (playMusic()) {
            btnPlay.setText("Pause");
        } else {
            btnPlay.setText("Play");
        }
    }

    private void updateCycleButton() {
        if (cycleMusic()) {
            btnCycle.setText("Not cycle");
            cycle = true;
        } else {
            btnCycle.setText("Cycle");
            cycle = false;
        }

        if (mediaPlayer != null) {
            cycle();
        }
    }

    private void cycle() {
        mediaPlayer.setLooping(cycle);
    }
}
package com.example.chinesecalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {
    TextView txtMoreInfo;
    TextView txtTitle;
    ImageView imgSign;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        initialize();
    }

    private void initialize() {
        txtMoreInfo = findViewById(R.id.txtMoreInfo);
        txtTitle = findViewById(R.id.txtTitle);
        imgSign = findViewById(R.id.imgSign);
        btnBack = findViewById(R.id.btnBack);

        getIntentData();
        setListeners();
    }

    private void getIntentData() {
        Intent intent = getIntent();

        String title = intent.getStringExtra("titulo");
        String message = intent.getStringExtra("message");
        int drawableImgId = intent.getIntExtra("drawableImgId", 0);

        txtTitle.setText(title);
        txtMoreInfo.setText(message);
        imgSign.setImageResource(drawableImgId);
    }

    private void setListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
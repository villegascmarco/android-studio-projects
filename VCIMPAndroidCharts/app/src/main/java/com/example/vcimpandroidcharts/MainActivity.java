package com.example.vcimpandroidcharts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtJanuary;
    TextView txtFebruary;
    TextView txtMarch;
    TextView txtApril;
    TextView txtMay;
    TextView txtJune;
    TextView txtJuly;
    TextView txtAugust;
    TextView txtSeptember;
    TextView txtOctober;
    TextView txtNovember;
    TextView txtDecember;
    Button btnGenerateChart;

    Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        txtJanuary = findViewById(R.id.txtJanuary);
        txtFebruary = findViewById(R.id.txtFebruary);
        txtMarch = findViewById(R.id.txtMarch);
        txtApril = findViewById(R.id.txtApril);
        txtMay = findViewById(R.id.txtMay);
        txtJune = findViewById(R.id.txtJune);
        txtJuly = findViewById(R.id.txtJuly);
        txtAugust = findViewById(R.id.txtAugust);
        txtSeptember = findViewById(R.id.txtSeptember);
        txtOctober = findViewById(R.id.txtOctober);
        txtNovember = findViewById(R.id.txtNovember);
        txtDecember = findViewById(R.id.txtDecember);
        btnGenerateChart = findViewById(R.id.btnGenerateChart);

        setListeners();
    }

    private void setListeners() {
        btnGenerateChart.setOnClickListener(v -> {
            createChart();
        });
    }

    private double[] getValues() {
        double january = Float.parseFloat(txtJanuary.getText().toString());
        double february = Float.parseFloat(txtFebruary.getText().toString());
        double march = Float.parseFloat(txtMarch.getText().toString());
        double april = Float.parseFloat(txtApril.getText().toString());
        double may = Float.parseFloat(txtMay.getText().toString());
        double june = Float.parseFloat(txtJune.getText().toString());
        double july = Float.parseFloat(txtJuly.getText().toString());
        double august = Float.parseFloat(txtAugust.getText().toString());
        double september = Float.parseFloat(txtSeptember.getText().toString());
        double october = Float.parseFloat(txtOctober.getText().toString());
        double november = Float.parseFloat(txtNovember.getText().toString());
        double december = Float.parseFloat(txtDecember.getText().toString());

        return new double[]{january,february,march,april,may,june,july,august,september,october,november,december};
    }

    private void createChart() {
        canvas = new Canvas(this);
        canvas.setBackgroundColor(Color.WHITE);
        canvas.setHeaderX(new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"});
        canvas.setValues(getValues());
        setContentView(canvas);
    }
}
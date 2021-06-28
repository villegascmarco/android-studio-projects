package com.example.celsius_fahrenheitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnConvert;
    TextView txtFarenheit;
    TextView txtCelcius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        btnConvert = this.findViewById(R.id.btnConvert);
        txtCelcius = this.findViewById(R.id.txtCelcius);
        txtFarenheit = this.findViewById(R.id.txtFarenheit);
        setListeners();
    }

    private void setListeners() {
        btnConvert.setOnClickListener(v -> {
            txtFarenheit.setText("" + convertToFahrenheit(getDegrees()));
        });
    }

    private double getDegrees() {
        String value = txtCelcius.getText().toString();

        if (value.isEmpty()) {
            return 0;
        }
        System.out.println(Double.parseDouble(value));
        return Double.parseDouble(value);

    }

    private double convertToFahrenheit(double celcius) {
        System.out.println(celcius * (9 / 5));
        return celcius * 1.8 + 32;
    }

    private double convertToCelcius(double fahrenheit) {
        return (fahrenheit - 32) * (5 / 9);
    }
}
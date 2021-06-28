package com.example.average;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button btnCalcular;
    TextView txtNumeros;
    TextView txtPromedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnCalcular = this.findViewById(R.id.btnCalcular);
        txtNumeros = this.findViewById(R.id.txtNumeros);
        txtPromedio = this.findViewById(R.id.txtPromedio);

        setListeners();
    }

    private void setListeners() {
        btnCalcular.setOnClickListener(e -> {
            calculateStandardDeviation();
        });
    }

    public void calculateStandardDeviation() {
        double avg;
        double sum = 0;

        Double[] numbers = getDoubleArray();
        int length = numbers.length;
        avg = getAvg(numbers);

        for (int i = 0; i < length; i++)
            sum += Math.pow(numbers[i] - avg, 2);

        txtPromedio.setText("" + Math.sqrt(sum / (double) (length-1)));//Desviación muestral
    }

    private Double[] getDoubleArray() {
        String[] numbers = txtNumeros.getText().toString().split(",");

        Double[] doubleNumeros = Arrays.stream(numbers)
                .map(Double::valueOf)
                .toArray(Double[]::new);
        return doubleNumeros;
    }

    private Double getAvg(Double[] numbers) {
        double acc = 0;
        double avg;

        for (Double number : numbers) {
            acc += number;
        }
        avg = acc / (numbers.length);

        return avg;
    }
}
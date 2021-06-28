package com.example.vcimppiechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnGenerateChart;
    private TextView txtSales;
    private TextView txtCostSales;

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        txtSales = findViewById(R.id.txtSales);
        txtCostSales = findViewById(R.id.txtCostSales);
        btnGenerateChart = findViewById(R.id.btnGenerateChart);

        setListeners();
    }

    private void setListeners() {
        btnGenerateChart.setOnClickListener(v -> {
            createPieChart();
        });
    }

    private void createPieChart() {
        pieChart = new PieChart(this);
        pieChart.setBackgroundColor(Color.WHITE);
        pieChart.setValues(getData());
        pieChart.setHeaders(new String[]{"Ventas netas", "Costo de ventas", "Utilidad"});
        setContentView(pieChart);
    }

    private float[] getData() {
        float sales = Float.parseFloat(txtSales.getText().toString());
        float costSales = Float.parseFloat(txtCostSales.getText().toString());
        float utility = sales - costSales;

        return new float[]{sales, costSales, utility};
    }

}
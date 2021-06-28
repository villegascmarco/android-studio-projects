package com.example.vcimpregression;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] HEADER = {"n", "x", "y", "x^2", "x*y", "y^2"};
    private TableLayout tbRegresion;
    private EditText txtXk;
    private EditText txtNumberX;
    private EditText txtNumberY;
    private Button btnAdd;
    private Button btnCalculate;
    private EditText txtB1;
    private EditText txtRXY;
    private EditText txtR2;
    private EditText txtB0;
    private EditText txtYK;
    private ArrayList<String[]> rows = new ArrayList<>();
    private DynamicTable dynamicTable;
    private Regression item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        tbRegresion = findViewById(R.id.tbRegresion);
        txtXk = findViewById(R.id.txtXk);
        txtNumberX = findViewById(R.id.txtNumberX);
        txtNumberY = findViewById(R.id.txtNumberY);
        btnAdd = findViewById(R.id.btnAdd);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtB1 = findViewById(R.id.txtB1);
        txtRXY = findViewById(R.id.txtRXY);
        txtR2 = findViewById(R.id.txtR2);
        txtB0 = findViewById(R.id.txtB0);
        txtYK = findViewById(R.id.txtYK);

        dynamicTable = new DynamicTable(tbRegresion, getApplicationContext());
        dynamicTable.addHeader(HEADER);
        dynamicTable.addData(getData());

        setListeners();
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            if (add()) {
                clearText(false);
            }
        });
        btnCalculate.setOnClickListener(v -> {
            calculate();
        });
    }

    private void clearText(boolean clearXk) {
        txtNumberX.setText("");
        txtNumberY.setText("");
        if (clearXk) {
            txtXk.setText("");
        }
    }

    private boolean add() {
        if (txtNumberX.getText().toString().isEmpty() || txtNumberY.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Agrege valores válidos en X y Y", Toast.LENGTH_LONG).show();
            return false;
        }
        item = new Regression(txtNumberX.getText().toString(),
                txtNumberY.getText().toString());

        dynamicTable.addItem(item);
        return true;
    }

    private void calculate() {

        if (!validateXK()) {
            return;
        }
        double b1 = calculateB1();
        double rXY = calculateRXY();
        double r2 = Math.pow(rXY, 2);
        double b0 = calculateB0(b1);
        double yk = calculateYK(b0, b1);

        txtB1.setText("B1: " + b1);
        txtRXY.setText("rXY: " + rXY);
        txtR2.setText("r2: " + r2);
        txtB0.setText("B0: " + b0);
        txtYK.setText("Yk: " + yk);
        changeElementStatus(true);
        clearText(true);
    }

    private boolean validateXK() {
        if (!txtXk.isEnabled()) {
            changeElementStatus(false);
            clearText(false);
            return false;
        }
        if (txtXk.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "No puede estar vacío el valor en XK", Toast.LENGTH_LONG).show();
            return false;
        }

        clearText(false);
        return true;
    }

    private void changeElementStatus(boolean status) {
        txtXk.setEnabled(!status);
        txtNumberX.setEnabled(status);
        txtNumberY.setEnabled(status);
        btnAdd.setEnabled(status);
    }

    private void test() {
        item = new Regression("130", "186");
        dynamicTable.addItem(item);
        item = new Regression("650", "699");
        dynamicTable.addItem(item);
        item = new Regression("99", "132");
        dynamicTable.addItem(item);
        item = new Regression("150", "272");
        dynamicTable.addItem(item);
        item = new Regression("128", "291");
        dynamicTable.addItem(item);
        item = new Regression("302", "331");
        dynamicTable.addItem(item);
        item = new Regression("95", "199");
        dynamicTable.addItem(item);
        item = new Regression("945", "1890");
        dynamicTable.addItem(item);
        item = new Regression("368", "788");
        dynamicTable.addItem(item);
        item = new Regression("961", "1601");
        dynamicTable.addItem(item);

    }

    private ArrayList<String[]> getData() {
        rows.add(new String[]{"Total", "", "", "", "", ""});

        return rows;
    }

    private double calculateB1() {
        int size = dynamicTable.getDataSize();
        double xAvg = dynamicTable.getxAvg();
        double b1 = dynamicTable.getSumXY() - (size * xAvg * dynamicTable.getyAvg());
        b1 /= dynamicTable.getSumX2() - (size * Math.pow(xAvg, 2));
        return b1;
    }

    private double calculateRXY() {
        int size = dynamicTable.getDataSize();
        double sumX = dynamicTable.getSumX();
        double sumY = dynamicTable.getSumY();

        double rxy = size * dynamicTable.getSumXY() - sumX * sumY;

        double divisor = size * dynamicTable.getSumX2() - Math.pow(sumX, 2);

        divisor *= size * dynamicTable.getSumY2() - Math.pow(sumY, 2);

        rxy /= Math.sqrt(divisor);

        return rxy;
    }

    private double calculateB0(double b1) {
        return dynamicTable.getyAvg() - b1 * dynamicTable.getxAvg();
    }

    private double calculateYK(double b0, double b1) {
        return b0 + b1 * Integer.parseInt(txtXk.getText().toString());
    }
}
package com.example.vcimpquote.car;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Car;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity {

    private TextView txtID;
    private TextView txtName;
    private TextView txtType;
    private TextView txtPrice;
    private Button btnAdd;
    private Button btnList;

    private String TABLE = "car";

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        initialize();
    }

    private void initialize() {
        txtID = findViewById(R.id.txtID);
        textViews.add(txtID);
        txtName = findViewById(R.id.txtName);
        textViews.add(txtName);
        txtType = findViewById(R.id.txtType);
        textViews.add(txtType);
        txtPrice = findViewById(R.id.txtPrice);
        textViews.add(txtPrice);

        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        initializeDB();

        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " ( id INTEGER PRIMARY KEY, name VARCHAR, type VARCHAR, price double );");
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            save();
        });
        btnList.setOnClickListener(v -> {
            list();
        });
    }

    private void save() {
        if (!validateData()) {
            return;
        }
        Car car = new Car();
        car.setId(Integer.valueOf(txtID.getText().toString()));
        car.setName(txtName.getText().toString());
        car.setType(txtType.getText().toString());
        car.setPrice(Double.valueOf(txtPrice.getText().toString()));

        db.execSQL("INSERT INTO " + TABLE + " VALUES(" + car.getDataQuery() + ");");

        clearData();
    }

    private void list() {
        Intent intent = new Intent(this, CarListActivity.class);
        startActivity(intent);
    }

    private boolean validateData() {
        for (TextView textView : textViews) {
            if (textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean clearData() {
        for (TextView textView : textViews) {
            textView.setText("");
        }
        return true;
    }
}
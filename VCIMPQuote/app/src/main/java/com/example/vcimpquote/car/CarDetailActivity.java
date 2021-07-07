package com.example.vcimpquote.car;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Car;
import com.model.vcimpquote.Customer;

import java.util.ArrayList;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {
    private TextView txtID;
    private TextView txtName;
    private TextView txtType;
    private TextView txtPrice;
    private Button btnSave;
    private Button btnDelete;

    private String TABLE = "car";

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        initialize();
        loadData(getIntent().getExtras().getInt("selectedID"));
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

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        initializeDB();

        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
    }

    private void setListeners() {
        btnSave.setOnClickListener(v -> {
            save();
        });
        btnDelete.setOnClickListener(v -> {
            delete();
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
        car.setPrice(Integer.valueOf(txtPrice.getText().toString()));


        db.execSQL("UPDATE " + TABLE + " SET " +
                "name = \"" + car.getName() + "\"," +
                "type = \"" + car.getType() + "\"," +
                "price = " + car.getPrice() + "" +
                " WHERE id = \"" + car.getId() + "\";"
        );
        finish();

        finish();
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

    private void delete() {
        String id = txtID.getText().toString();

        db.execSQL("DELETE FROM " + TABLE + " WHERE id = " + id + ";");
        db.execSQL("DELETE FROM quote WHERE car = " + id + ";");
        finish();
    }

    private void loadData(int selectedID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id='" + selectedID + "'", null);
        if (cursor.getCount() == 0) {
            finish();
        }
        while (cursor.moveToNext()) {
            txtID.setText(cursor.getString(0));
            txtName.setText(cursor.getString(1));
            txtType.setText(cursor.getString(2));
            txtPrice.setText(cursor.getString(3));
        }


    }
}
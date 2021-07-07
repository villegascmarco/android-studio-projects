package com.example.vcimpquote.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Vendor;

import java.util.ArrayList;
import java.util.List;

public class VendorActivity extends AppCompatActivity {

    private TextView txtID;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtPosition;
    private TextView txtSalary;
    private Button btnAdd;
    private Button btnList;

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        initialize();
    }

    private void initialize() {
        txtID = findViewById(R.id.txtID);
        textViews.add(txtID);
        txtName = findViewById(R.id.txtName);
        textViews.add(txtName);
        txtEmail = findViewById(R.id.txtEmail);
        textViews.add(txtEmail);
        txtPosition = findViewById(R.id.txtPosition);
        textViews.add(txtPosition);
        txtSalary = findViewById(R.id.txtSalary);
        textViews.add(txtSalary);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        initializeDB();

        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("CREATE TABLE IF NOT EXISTS vendor ( id INTEGER PRIMARY KEY, name VARCHAR, email VARCHAR, position VARCHAR, salary double );");
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
        Vendor vendor = new Vendor();
        vendor.setId(Integer.valueOf(txtID.getText().toString()));
        vendor.setName(txtName.getText().toString());
        vendor.setEmail(txtEmail.getText().toString());
        vendor.setPosition(txtPosition.getText().toString());
        vendor.setSalary(Double.valueOf(txtSalary.getText().toString()));

        db.execSQL("INSERT INTO vendor VALUES(" + vendor.getDataQuery() + ");");

        clearData();
    }

    private void list() {
        Intent intent = new Intent(this, VendorListActivity.class);
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
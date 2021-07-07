package com.example.vcimpquote.customer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private TextView txtID;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtAge;
    private Button btnAdd;
    private Button btnList;

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        initialize();
    }

    private void initialize() {
        txtID = findViewById(R.id.txtID);
        textViews.add(txtID);
        txtName = findViewById(R.id.txtName);
        textViews.add(txtName);
        txtEmail = findViewById(R.id.txtEmail);
        textViews.add(txtEmail);
        txtAge = findViewById(R.id.txtAge);
        textViews.add(txtAge);

        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        initializeDB();

        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("CREATE TABLE IF NOT EXISTS customer ( id INTEGER PRIMARY KEY, name VARCHAR, email VARCHAR, salary integer );");
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
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(txtID.getText().toString()));
        customer.setName(txtName.getText().toString());
        customer.setEmail(txtEmail.getText().toString());
        customer.setAge(Integer.valueOf(txtAge.getText().toString()));

        db.execSQL("INSERT INTO customer VALUES(" + customer.getDataQuery() + ");");

        clearData();
    }

    private void list() {
        Intent intent = new Intent(this, CustomerListActivity.class);
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
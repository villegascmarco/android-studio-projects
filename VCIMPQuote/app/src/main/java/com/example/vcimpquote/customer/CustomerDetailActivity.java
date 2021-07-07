package com.example.vcimpquote.customer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Customer;
import com.model.vcimpquote.Vendor;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailActivity extends AppCompatActivity {
    private TextView txtID;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtAge;
    private Button btnSave;
    private Button btnDelete;

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        initialize();
        loadData(getIntent().getExtras().getInt("selectedID"));
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
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(txtID.getText().toString()));
        customer.setName(txtName.getText().toString());
        customer.setEmail(txtEmail.getText().toString());
        customer.setAge(Integer.valueOf(txtAge.getText().toString()));


        db.execSQL("UPDATE customer SET " +
                "name = \"" + customer.getName() + "\"," +
                "email = \"" + customer.getEmail() + "\"," +
                "salary = " + customer.getAge() + "" +
                " WHERE id = \"" + customer.getId() + "\";"
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

        db.execSQL("DELETE FROM customer WHERE id = " + id + ";");
        db.execSQL("DELETE FROM quote WHERE customer = " + id + ";");
        finish();
    }

    private void loadData(int selectedID) {
        Cursor cursor = db.rawQuery("SELECT * FROM customer WHERE id='" + selectedID + "'", null);
        if (cursor.getCount() == 0) {
            finish();
        }
        while (cursor.moveToNext()) {
            txtID.setText(cursor.getString(0));
            txtName.setText(cursor.getString(1));
            txtEmail.setText(cursor.getString(2));
            txtAge.setText(cursor.getString(3));
        }


    }
}
package com.example.vcimpquote.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcimpquote.R;
import com.model.vcimpquote.Vendor;

import java.util.ArrayList;
import java.util.List;

public class VendorDetailActivity extends AppCompatActivity {
    private TextView txtID;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtPosition;
    private TextView txtSalary;
    private Button btnSave;
    private Button btnDelete;

    private List<TextView> textViews = new ArrayList<>();

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);

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
        txtPosition = findViewById(R.id.txtPosition);
        textViews.add(txtPosition);
        txtSalary = findViewById(R.id.txtSalary);
        textViews.add(txtSalary);
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
        Vendor vendor = new Vendor();
        vendor.setId(Integer.valueOf(txtID.getText().toString()));
        vendor.setName(txtName.getText().toString());
        vendor.setEmail(txtEmail.getText().toString());
        vendor.setPosition(txtPosition.getText().toString());
        vendor.setSalary(Double.valueOf(txtSalary.getText().toString()));


        db.execSQL("UPDATE vendor SET " +
                "name = \"" + vendor.getName() + "\"," +
                "email = \"" + vendor.getEmail() + "\"," +
                "position = \"" + vendor.getPosition() + "\"," +
                "salary = " + vendor.getSalary() + "" +
                " WHERE id = \"" + vendor.getId() + "\";"
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

        db.execSQL("DELETE FROM vendor WHERE id = " + id + ";");
        db.execSQL("DELETE FROM quote WHERE vendor = " + id + ";");
        finish();
    }

    private void loadData(int selectedID) {
        Cursor cursor = db.rawQuery("SELECT * FROM vendor WHERE id='" + selectedID + "'", null);
        if (cursor.getCount() == 0) {
            finish();
        }
        while (cursor.moveToNext()) {
            txtID.setText(cursor.getString(0));
            txtName.setText(cursor.getString(1));
            txtEmail.setText(cursor.getString(2));
            txtPosition.setText(cursor.getString(3));
            txtSalary.setText(cursor.getString(4));
        }


    }
}
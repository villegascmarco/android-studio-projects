package com.example.vcimpsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ItemDetail extends AppCompatActivity {

    EditText txtRollNo;
    EditText txtName;
    EditText txtDepartment;
    EditText txtPosition;
    EditText txtStreet;
    EditText txtNeighbor;
    EditText txtSalary;
    private Button btnUpdate;
    private Button btnDelete;

    private final String DELIMITER = "#,#";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        initialize();

        loadData(getIntent().getExtras().getString("currentValue"));
    }

    private void initialize() {
        txtRollNo = findViewById(R.id.txtRollNo);
        txtName = findViewById(R.id.txtName);
        txtDepartment = findViewById(R.id.txtDepartment);
        txtPosition = findViewById(R.id.txtPosition);
        txtStreet = findViewById(R.id.txtStreet);
        txtNeighbor = findViewById(R.id.txtNeighbor);
        txtSalary = findViewById(R.id.txtSalary);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        setListeners();
        initializeDataBase();
    }

    private void setListeners() {
        btnUpdate.setOnClickListener(v -> {
            update();
        });

        btnDelete.setOnClickListener(v -> {
            delete();
        });
    }

    private void initializeDataBase() {
        db = openOrCreateDatabase("Empleados", Context.MODE_PRIVATE, null);
    }

    private void update() {
        String id = txtRollNo.getText().toString();

        db.execSQL("UPDATE empleado SET " +
                "nombre = \"" + txtName.getText() + "\"," +
                "dep = \"" + txtDepartment.getText() + "\"," +
                "puesto = \"" + txtPosition.getText() + "\"," +
                "calle = \"" + txtStreet.getText() + "\"," +
                "colonia = \"" + txtNeighbor.getText() + "\"," +
                "sueldo = \"" + txtSalary.getText() + "\"" +
                " WHERE clave = \"" + txtRollNo.getText() + "\";"
        );
        finish();
    }

    private void delete() {
        String id = txtRollNo.getText().toString();

        db.execSQL("DELETE FROM empleado WHERE clave = " + id + ";");
        finish();
    }

    private void loadData(String object) {
        String data[] = object.split(DELIMITER);

        if (data.length == 0) {
            return;
        }

        txtRollNo.setText(data[0]);
        txtName.setText(data[1]);
        txtDepartment.setText(data[2]);
        txtPosition.setText(data[3]);
        txtStreet.setText(data[4]);
        txtNeighbor.setText(data[5]);
        txtSalary.setText(data[6]);

    }

}
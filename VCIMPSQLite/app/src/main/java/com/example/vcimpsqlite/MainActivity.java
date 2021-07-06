package com.example.vcimpsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtRollNo;
    EditText txtName;
    EditText txtDepartment;
    EditText txtPosition;
    EditText txtStreet;
    EditText txtNeighbor;
    EditText txtSalary;
    Button btnAdd;
    Button btnDelete;
    Button btnModify;
    Button btnView;
    Button btnViewAll;

    List<EditText> listEditText = new ArrayList<>();
    private final String DELIMITER = "#,#";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        txtRollNo = findViewById(R.id.txtRollNo);
        listEditText.add(txtRollNo);
        txtName = findViewById(R.id.txtName);
        listEditText.add(txtName);
        txtDepartment = findViewById(R.id.txtDepartment);
        listEditText.add(txtDepartment);
        txtPosition = findViewById(R.id.txtPosition);
        listEditText.add(txtPosition);
        txtStreet = findViewById(R.id.txtStreet);
        listEditText.add(txtStreet);
        txtNeighbor = findViewById(R.id.txtNeighbor);
        listEditText.add(txtNeighbor);
        txtSalary = findViewById(R.id.txtSalary);
        listEditText.add(txtSalary);

        btnAdd = findViewById(R.id.btnAdd);
        btnViewAll = findViewById(R.id.btnViewAll);

        setListeners();
        initializeDataBase();
    }

    private void setListeners() {
        btnAdd.setOnClickListener(v -> {
            addRecord();
        });
        btnViewAll.setOnClickListener(v -> {
            openIntent();
        });
    }

    private void openIntent() {
        Intent intent = new Intent(this, ItemList.class);
        intent.putStringArrayListExtra("item_list", (ArrayList<String>) getFileData());
        startActivity(intent);
    }

    private void initializeDataBase() {
        db = openOrCreateDatabase("Empleados", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS empleado(clave VARCHAR,nombre VARCHAR,dep VARCHAR,puesto VARCHAR,calle VARCHAR,colonia VARCHAR,sueldo VARCHAR);");
    }

    private void addRecord() {
        String data = getData();
        if (data.isEmpty()) {
            return;
        }

        db.execSQL("INSERT INTO empleado VALUES(" + data + ");");
        clearData();
    }

    private void clearData() {
        for (EditText editText : listEditText) {
            editText.setText("");
        }
    }

    private String getData() {
        String output = "";
        String currentValue;
        for (EditText editText : listEditText) {
            currentValue = editText.getText().toString();

            if (currentValue.isEmpty()) {
                Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
                output = "";
                break;
            } else {
                output += "\"" + currentValue + "\" ,";
            }
        }
        System.out.println(output);
        if (!output.isEmpty()) {
            output = output.substring(0, output.length() - 1);
        }

        return output;
    }

    private List<String> getFileData() {
        List<String> data = new ArrayList<>();
        String currentValue = "";

        Cursor cursor = db.rawQuery("SELECT * FROM empleado", null);
        if (cursor.getCount() == 0) {
            return data;
        }

        while (cursor.moveToNext()) {
            currentValue = "";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                currentValue += cursor.getString(i) + DELIMITER;
            }
            data.add(currentValue);
        }

        return data;
    }
}
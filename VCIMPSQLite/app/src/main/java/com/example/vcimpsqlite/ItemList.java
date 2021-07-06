package com.example.vcimpsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    private TableLayout tableItem;
    private TextView txtSearchID;
    private Button btnDetail;

    private final String DELIMITER = "#,#";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        initialize();
        loadData();
    }


    private void initialize() {
        tableItem = findViewById(R.id.tableItem);
        txtSearchID = findViewById(R.id.txtSearchID);
        btnDetail = findViewById(R.id.btnDetail);

        setListeners();
        initializeDataBase();
    }

    private void setListeners() {
        btnDetail.setOnClickListener(v -> {
            openIntent();
        });
    }

    private void initializeDataBase() {
        db = openOrCreateDatabase("Empleados", Context.MODE_PRIVATE, null);
    }

    private void loadData() {
        ArrayList<String> items = getIntent().getStringArrayListExtra("item_list");

        if (items == null) {
            return;
        }

        for (String item : items) {
            System.out.println(item);
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            String[] data = item.split("#,#");

            for (int i = 0; i < 4; i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setWidth(270);
                textView.setGravity(Gravity.CENTER);
                textView.setText(data[i]);
                tableRow.addView(textView);
            }
            tableItem.addView(tableRow);
        }
    }

    private void openIntent() {
        String currentValue = "";
        Cursor cursor = db.rawQuery("SELECT * FROM empleado WHERE clave='" + txtSearchID.getText() + "'", null);

        while (cursor.moveToNext()) {
            currentValue = "";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                currentValue += cursor.getString(i) + DELIMITER;
            }
        }
        Intent intent = new Intent(this, ItemDetail.class);
        intent.putExtra("currentValue", currentValue);
        startActivity(intent);
    }
}
package com.example.vcimpquote.customer;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.vcimpquote.R;
import com.example.vcimpquote.vendor.VendorDetailActivity;

public class CustomerListActivity extends AppCompatActivity {
    private TableLayout tableItem;
    private TextView txtSearchID;
    private Button btnDetail;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        initialize();
        loadData();
    }

    private void initialize() {
        tableItem = findViewById(R.id.tableItem);
        txtSearchID = findViewById(R.id.txtSearchID);
        btnDetail = findViewById(R.id.btnDetail);

        initializeDB();
        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
    }

    private void setListeners() {
        btnDetail.setOnClickListener(v -> {
            openIntent();
        });
    }

    private void openIntent() {
        String txt = txtSearchID.getText().toString();

        if (txt.isEmpty()) {
            return;
        }
        int selectedID = Integer.parseInt(txt);
        if (selectedID == 0) {
            return;
        }
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("selectedID", selectedID);
        startActivity(intent);
    }

    private void loadData() {

        Cursor cursor = db.rawQuery("SELECT * FROM customer", null);
        if (cursor.getCount() == 0) {
            return;
        }

        while (cursor.moveToNext()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setWidth(270);
                textView.setGravity(Gravity.CENTER);
                textView.setText(cursor.getString(i));
                tableRow.addView(textView);
            }
            tableItem.addView(tableRow);
        }
    }

}
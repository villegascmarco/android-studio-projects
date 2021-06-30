package com.example.vcimpexternalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsList extends AppCompatActivity {

    private TableLayout tableItem;
    private TextView txtSearchID;
    private Button btnDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        initialize();
        loadData();
    }

    private void initialize() {
        tableItem = findViewById(R.id.tableItem);
        txtSearchID = findViewById(R.id.txtSearchID);
        btnDetail = findViewById(R.id.btnDetail);

        setListeners();
    }

    private void setListeners() {
        btnDetail.setOnClickListener(v -> {
            openIntent();
        });
    }

    private void loadData() {
        ArrayList<String> items = getIntent().getStringArrayListExtra("item_list");

        if (items == null) {
            return;
        }
/**
 *
 *  android:id="@+id/rowInternalID"
 *                 android:layout_width="0dp"
 *                 android:layout_height="wrap_content"
 *                 android:layout_weight="4"
 *                 android:padding="10sp"
 *                 android:text="ID"
 *                 android:textSize="14sp" />
 *
 */

        for (String item : items) {
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
        String txt = txtSearchID.getText().toString();

        if (txt.isEmpty()) {
            return;
        }
        int selectedID = Integer.parseInt(txt);
        if (selectedID == 0) {
            return;
        }
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("selectedID", selectedID);
        startActivity(intent);
    }

}
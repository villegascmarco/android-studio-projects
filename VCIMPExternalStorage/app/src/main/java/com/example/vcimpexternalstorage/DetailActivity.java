package com.example.vcimpexternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView txtID;
    private TextView txtCode;
    private TextView txtName;
    private TextView txtLine;
    private TextView txtUnitMeasure;
    private TextView txtInventory;
    private TextView txtUnitCost;
    private TextView txtAvgCost;
    private TextView txtPrice;
    private Button btnUpdate;
    private Button btnDelete;

    private List<TextView> txtBoxes = new ArrayList<>();
    private final String FILE_NAME = "items.txt";
    private final String DELIMITER = "#,#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();

        loadData(getIntent().getExtras().getInt("selectedID"));
    }

    private void initialize() {
        txtID = findViewById(R.id.txtID);
        txtCode = findViewById(R.id.txtCode);
        txtName = findViewById(R.id.txtSearchID);
        txtLine = findViewById(R.id.txtLine);
        txtUnitMeasure = findViewById(R.id.txtUnitMeasure);
        txtInventory = findViewById(R.id.txtInventory);
        txtUnitCost = findViewById(R.id.txtUnitCost);
        txtAvgCost = findViewById(R.id.txtAvgCost);
        txtPrice = findViewById(R.id.txtPrice);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        fillList();
        setListeners();
    }

    private void setListeners() {
        btnUpdate.setOnClickListener(v -> {
            update();
        });

        btnDelete.setOnClickListener(v -> {
            delete();
        });
    }

    private void fillList() {
        txtBoxes.add(txtID);
        txtBoxes.add(txtCode);
        txtBoxes.add(txtName);
        txtBoxes.add(txtLine);
        txtBoxes.add(txtUnitMeasure);
        txtBoxes.add(txtInventory);
        txtBoxes.add(txtUnitCost);
        txtBoxes.add(txtAvgCost);
        txtBoxes.add(txtPrice);
    }

    private void update() {
        String data = getData();
        String id = txtID.getText().toString() + DELIMITER;
        if (data == null) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Path path = getFile().toPath();
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(id)) {
                    fileContent.set(i, data);
                    break;
                }
            }

            Files.write(path, fileContent, StandardCharsets.UTF_8);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void delete(){
        String data = getData();
        String id = txtID.getText().toString() + DELIMITER;
        if (data == null) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Path path = getFile().toPath();
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(id)) {
                    fileContent.remove(i);
                    break;
                }
            }

            Files.write(path, fileContent, StandardCharsets.UTF_8);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getData() {
        String data = "";
        for (TextView textView : txtBoxes) {
            String value = textView.getText().toString();

            if (value.isEmpty()) {
                return null;
            }
            data += value + DELIMITER;
        }
        data += "\n";
        return data;
    }

    private void loadData(int itemID) {
        String data[] = getItem(itemID).split(DELIMITER);

        if (data.length == 0) {
            return;
        }

        txtID.setText(data[0]);
        txtCode.setText(data[1]);
        txtName.setText(data[2]);
        txtLine.setText(data[3]);
        txtUnitMeasure.setText(data[4]);
        txtInventory.setText(data[5]);
        txtUnitCost.setText(data[6]);
        txtAvgCost.setText(data[7]);
        txtPrice.setText(data[8]);

    }

    private String getItem(int itemID) {
        String line;
        String item = "";
        File file = getFile();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(file.getAbsolutePath()));

            while ((line = fin.readLine()) != null) {
                if (line.startsWith("" + itemID)) {
                    item = line;
                    break;
                }
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    private File getFile() {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myFile = new File(folder, FILE_NAME);
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return myFile;
    }
}
package com.example.vcimpexternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtID;
    private TextView txtName;
    private TextView txtLine;
    private TextView txtUnitMeasure;
    private TextView txtInventory;
    private TextView txtUnitCost;
    private TextView txtAvgCost;
    private TextView txtPrice;
    private Button btnSave;
    private Button btnDeleteAll;
    private Button btnList;

    private List<TextView> txtBoxes = new ArrayList<>();
    private final String FILE_NAME = "items.txt";
    private final String DELIMITER = "#,#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtSearchID);
        txtLine = findViewById(R.id.txtLine);
        txtUnitMeasure = findViewById(R.id.txtUnitMeasure);
        txtInventory = findViewById(R.id.txtInventory);
        txtUnitCost = findViewById(R.id.txtUnitCost);
        txtAvgCost = findViewById(R.id.txtAvgCost);
        txtPrice = findViewById(R.id.txtPrice);
        btnSave = findViewById(R.id.btnSave);
        btnDeleteAll = findViewById(R.id.btnDelete);
        btnList = findViewById(R.id.btnList);

        fillList();
        setListeners();
    }

    private void fillList() {
        txtBoxes.add(txtID);
        txtBoxes.add(txtName);
        txtBoxes.add(txtLine);
        txtBoxes.add(txtUnitMeasure);
        txtBoxes.add(txtInventory);
        txtBoxes.add(txtUnitCost);
        txtBoxes.add(txtAvgCost);
        txtBoxes.add(txtPrice);
    }

    private void setListeners() {
        btnSave.setOnClickListener(v -> {
            save();
        });
        btnDeleteAll.setOnClickListener(v -> {
            deleteAllData();
        });
        btnList.setOnClickListener(v -> {
            openIntent();
        });
    }

    private void save() {
        String data = getData();

        if (data == null) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
            return;
        }
        data = getID() + DELIMITER + data;
        try {
            File myFile = getFile();
            PrintWriter out = new PrintWriter(new FileWriter(myFile, true));
            out.append(data);
            out.close();


            clearData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteAllData() {
        File file = getFile();
        file.delete();
        clearData();
    }

    private int getID() {
        InputStream is = null;
        try {
            is = new FileInputStream(getFile());
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            is.close();
            return (count == 0 && !empty) ? 1 : count + 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void openIntent() {
        Intent intent = new Intent(this, ItemsList.class);
        intent.putStringArrayListExtra("item_list", (ArrayList<String>) getFileData());
        startActivity(intent);
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

    private void clearData() {
        for (TextView textView : txtBoxes) {
            textView.setText("");
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
        System.out.println(data);
        return data;
    }

    private List<String> getFileData() {
        List<String> data = new ArrayList<>();
        String line;
        File file = getFile();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(file.getAbsolutePath()));

            while ((line = fin.readLine()) != null) {
                if (!line.isEmpty()) {
                    System.out.println("-------" + line);
                    data.add(line);
                }
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(data.size());
        return data;
    }
}
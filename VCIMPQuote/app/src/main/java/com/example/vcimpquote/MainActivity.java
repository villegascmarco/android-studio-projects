package com.example.vcimpquote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.vcimpquote.car.CarActivity;
import com.example.vcimpquote.customer.CustomerActivity;
import com.example.vcimpquote.quote.QuoteActivity;
import com.example.vcimpquote.vendor.VendorActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnVendors;
    private Button btnCustomers;
    private Button btnCars;
    private Button btnQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }


    private void initialize() {
        btnVendors = findViewById(R.id.btnVendors);
        btnCustomers = findViewById(R.id.btnCustomers);
        btnCars = findViewById(R.id.btnCars);
        btnQuotes = findViewById(R.id.btnQuotes);

        setListeners();
    }

    private void setListeners() {
        btnVendors.setOnClickListener(v -> {
            changeIntent(VendorActivity.class);
        });
        btnCustomers.setOnClickListener(v -> {
            changeIntent(CustomerActivity.class);
        });
        btnCars.setOnClickListener(v -> {
            changeIntent(CarActivity.class);
        });
        btnQuotes.setOnClickListener(v -> {
            changeIntent(QuoteActivity.class);
        });
    }

    public void changeIntent(Class newIntent) {
        Intent intent = new Intent(this, newIntent);
        startActivity(intent);
    }
}
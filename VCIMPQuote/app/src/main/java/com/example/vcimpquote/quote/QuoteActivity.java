package com.example.vcimpquote.quote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcimpquote.R;
import com.example.vcimpquote.car.CarSpin;
import com.example.vcimpquote.customer.CustomerSpin;
import com.example.vcimpquote.vendor.VendorListActivity;
import com.example.vcimpquote.vendor.VendorSpin;
import com.model.vcimpquote.Car;
import com.model.vcimpquote.Customer;
import com.model.vcimpquote.Quote;
import com.model.vcimpquote.Vendor;

import java.util.ArrayList;
import java.util.List;

public class QuoteActivity extends AppCompatActivity {


    private VendorSpin vendorSpin;
    private CustomerSpin customerSpin;
    private CarSpin carSpin;

    private TextView txtID;
    private TextView txtDate;
    private Spinner spnVendor;
    private Spinner spnCustomer;
    private Spinner spnCar;
    private TextView txtAmount;
    private TextView txtDownpaymentPer;
    private TextView txtDownpayment;
    private TextView txtRemainingAmount;
    private Spinner spnPaymentTerms;
    private TextView txtInterestRate;
    private TextView txtIAnualnterestRate;
    private Button btnAdd;
    private Button btnList;

    private int IntVendor;
    private int IntCustomer;
    private int IntCar;

    private String paymentTerms;

    private List<TextView> textViews = new ArrayList<>();
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        initialize();
    }

    private void initialize() {
        initializeDB();

        txtID = findViewById(R.id.txtID);
        textViews.add(txtID);
        txtDate = findViewById(R.id.txtDate);
        textViews.add(txtDate);
        spnVendor = findViewById(R.id.spnVendor);
        spnCustomer = findViewById(R.id.spnCustomer);
        spnCar = findViewById(R.id.spnCar);
        txtAmount = findViewById(R.id.txtAmount);
        textViews.add(txtAmount);
        txtDownpaymentPer = findViewById(R.id.txtDownpaymentPer);
        textViews.add(txtDownpaymentPer);
        txtDownpayment = findViewById(R.id.txtDownpayment);
        textViews.add(txtDownpayment);
        txtRemainingAmount = findViewById(R.id.txtRemainingAmount);
        textViews.add(txtRemainingAmount);
        spnPaymentTerms = findViewById(R.id.spnPaymentTerms);
        txtInterestRate = findViewById(R.id.txtInterestRate);
        textViews.add(txtInterestRate);
        txtIAnualnterestRate = findViewById(R.id.txtIAnualnterestRate);
        textViews.add(txtIAnualnterestRate);

        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);

        vendorSpin = new VendorSpin(QuoteActivity.this, android.R.layout.simple_spinner_item,
                getAllVendors());
        customerSpin = new CustomerSpin(this, android.R.layout.simple_spinner_item,
                getAllCustomers());
        carSpin = new CarSpin(this, android.R.layout.simple_spinner_item,
                getAllCars());

        setListeners();
        setNewID();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);

        db.execSQL("PRAGMA foreign_keys=ON");
        //db.execSQL("DROP TABLE IF EXISTS quote;");
        db.execSQL("CREATE TABLE IF NOT EXISTS quote ( " +
                "id INTEGER PRIMARY KEY, " +
                "date VARCHAR, " +
                "vendor INTEGER, " +
                "customer INTEGER, " +
                "car INTEGER, " +
                "downpayment double, " +
                "paymentTerms int,  " +
                "interestRate int, " +
                "CONSTRAINT fk_vendors" +
                "    FOREIGN KEY (vendor)" +
                "    REFERENCES vendor(id) " +
                "    ON DELETE CASCADE, " +
                "CONSTRAINT fk_customers" +
                "    FOREIGN KEY (customer)" +
                "    REFERENCES customer(id) " +
                "    ON DELETE CASCADE, " +
                "CONSTRAINT fk_cars" +
                "    FOREIGN KEY (car)" +
                "    REFERENCES car(id) " +
                "    ON DELETE CASCADE);");
    }

    private void setNewID() {
        Cursor cursor = db.rawQuery("SELECT * FROM quote ORDER BY id DESC LIMIT 1", null);

        if (cursor.getCount() == 0) {
            txtID.setText("" + 1);
            return;
        }

        cursor.moveToNext();
        int id = cursor.getInt(0) + 1;
        txtID.setText("" + id);
    }

    private void setListeners() {
        spnVendor.setAdapter(vendorSpin);
        spnVendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Vendor vendor = vendorSpin.getItem(position);
                IntVendor = vendor.getId();
                // Here you can do the action you want to...
                //Toast.makeText(QuoteActivity.this, "ID: " + vendor.getId() + "\nName: " + vendor.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        spnCustomer.setAdapter(customerSpin);
        spnCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Customer customer = customerSpin.getItem(position);
                IntCustomer = customer.getId();
                // Here you can do the action you want to...
                //Toast.makeText(QuoteActivity.this, "ID: " + customer.getId() + "\nName: " + customer.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        spnCar.setAdapter(carSpin);
        spnCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Car car = carSpin.getItem(position);
                IntCar = car.getId();
                // Here you can do the action you want to...
                txtAmount.setText("" + car.getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.paymentTerms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPaymentTerms.setAdapter(adapter);
        spnPaymentTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                paymentTerms = adapterView.getItemAtPosition(position).toString();
                // Here you can do the action you want to...
                double interestRate = 0;
                switch (paymentTerms) {
                    case "12":
                        interestRate = 1.5;
                        break;
                    case "24":
                        interestRate = 2;
                        break;
                    case "36":
                        interestRate = 3;
                        break;
                    case "48":
                        interestRate = 3.5;
                        break;
                    case "60":
                        interestRate = 4;
                        break;
                }
                txtInterestRate.setText("" + interestRate);
                int year = Integer.parseInt(paymentTerms);
                txtIAnualnterestRate.setText("" + (year * interestRate));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        txtDownpaymentPer.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.toString().isEmpty()) {
                    double percentage = Double.parseDouble(s.toString()) / 100;
                    double amount = Double.parseDouble(txtAmount.getText().toString());
                    double downPayment = amount * percentage;
                    txtDownpayment.setText("" + downPayment);
                    txtRemainingAmount.setText("" + (amount - downPayment));
                }
            }
        });

        btnAdd.setOnClickListener(v -> {
            add();
        });
        btnList.setOnClickListener(v -> {
            list();
        });
    }

    private void add() {
        if (!validateData()) {
            return;
        }
        Quote quote = new Quote();
        quote.setId(Integer.parseInt(txtID.getText().toString()));
        quote.setDate(txtDate.getText().toString());
        quote.setVendor(IntVendor);
        quote.setCustomer(IntCustomer);
        quote.setCar(IntCar);
        quote.setRemainingAmount(Double.parseDouble(txtRemainingAmount.getText().toString()));
        quote.setPaymentTerms(Integer.parseInt(paymentTerms));
        quote.setInterestRate(Double.parseDouble(txtInterestRate.getText().toString()));

        db.execSQL("INSERT INTO quote VALUES(" + quote.getDataQuery() + ");");

        clearData();
    }

    private void list() {
        Intent intent = new Intent(this, QuoteListActivity.class);
        startActivity(intent);
    }

    private List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        Vendor vendor;
        Cursor cursor = db.rawQuery("SELECT * FROM vendor", null);
        if (cursor.getCount() == 0) {
            return vendors;
        }

        while (cursor.moveToNext()) {
            vendor = new Vendor();
            vendor.setId(cursor.getInt(0));
            vendor.setName(cursor.getString(1));
            vendor.setEmail(cursor.getString(2));
            vendor.setPosition(cursor.getString(3));
            vendor.setSalary(cursor.getDouble(4));

            vendors.add(vendor);
        }

        return vendors;
    }

    private List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer;
        Cursor cursor = db.rawQuery("SELECT * FROM customer", null);
        if (cursor.getCount() == 0) {
            return customers;
        }

        while (cursor.moveToNext()) {
            customer = new Customer();
            customer.setId(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setEmail(cursor.getString(2));
            customer.setAge(cursor.getInt(3));

            customers.add(customer);
        }

        return customers;
    }

    private List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        Car car;
        Cursor cursor = db.rawQuery("SELECT * FROM car", null);
        if (cursor.getCount() == 0) {
            return cars;
        }

        while (cursor.moveToNext()) {
            car = new Car();
            car.setId(cursor.getInt(0));
            car.setName(cursor.getString(1));
            car.setType(cursor.getString(2));
            car.setPrice(cursor.getDouble(3));

            cars.add(car);
        }

        return cars;
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

    private boolean clearData() {
        for (TextView textView : textViews) {
            textView.setText("");
        }
        return true;
    }
}
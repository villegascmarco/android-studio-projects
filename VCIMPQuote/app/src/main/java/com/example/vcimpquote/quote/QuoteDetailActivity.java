package com.example.vcimpquote.quote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vcimpquote.R;
import com.example.vcimpquote.car.CarSpin;
import com.example.vcimpquote.customer.CustomerDetailActivity;
import com.example.vcimpquote.customer.CustomerSpin;
import com.example.vcimpquote.vendor.VendorSpin;
import com.model.vcimpquote.Car;
import com.model.vcimpquote.Customer;
import com.model.vcimpquote.Quote;
import com.model.vcimpquote.Vendor;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuoteDetailActivity extends AppCompatActivity {
    private TableLayout tableItem;

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
    private Button btnSave;
    private Button btnDelete;

    private TextView txtToCapital;
    private TextView txtToInterest;
    private TextView txtPayToBank;

    private int IntVendor;
    private int IntCustomer;
    private int IntCar;

    private String paymentTerms;

    private double malaPractica;
    private boolean malaPractica2 = false;
    private double totalCapital;
    private double totalInterest;
    private double totalBank;

    private List<TextView> textViews = new ArrayList<>();
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);

        initialize();
        loadData(getIntent().getExtras().getInt("selectedID"));
    }

    private void initialize() {
        tableItem = findViewById(R.id.tableItem);

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

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        txtToCapital = findViewById(R.id.txtToCapital);
        txtToInterest = findViewById(R.id.txtToInterest);
        txtPayToBank = findViewById(R.id.txtPayToBank);

        vendorSpin = new VendorSpin(this, android.R.layout.simple_spinner_item,
                getAllVendors());
        customerSpin = new CustomerSpin(this, android.R.layout.simple_spinner_item,
                getAllCustomers());
        carSpin = new CarSpin(this, android.R.layout.simple_spinner_item,
                getAllCars());

        setListeners();
    }

    private void initializeDB() {
        db = openOrCreateDatabase("Quote", Context.MODE_PRIVATE, null);
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
                if (!malaPractica2) {
                    malaPractica2 = true;
                    txtDownpaymentPer.setText("" + (100 - malaPractica * 100 / Double.parseDouble(txtAmount.getText().toString())));
                }
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
        btnSave.setOnClickListener(v -> {
            save();
        });
        btnDelete.setOnClickListener(v -> {
            delete();
        });
    }

    private void delete() {
        db.execSQL("DELETE FROM quote WHERE id = " + getIntent().getExtras().getInt("selectedID"));
        finish();
    }

    private void loadData(int selectedID) {
        DecimalFormat df = new DecimalFormat("#.00");
        Date date;
        double interest;
        double accInterest;
        double acc;
        double acc2;
        double capital;

        double toCapital = 0;
        double toInterest = 0;
        double payToBank = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM quote where id = " + selectedID, null);
        if (cursor.getCount() == 0) {
            return;
        }
        cursor.moveToNext();

        Quote quote = new Quote();
        quote.setId(cursor.getInt(0));
        txtID.setText("" + quote.getId());
        quote.setDate(cursor.getString(1));
        txtDate.setText(quote.getDate());
        quote.setVendor(cursor.getInt(2));
        spnVendor.setSelection(quote.getVendor() - 1);
        quote.setCustomer(cursor.getInt(3));
        spnCustomer.setSelection(quote.getCustomer() - 1);
        quote.setCar(cursor.getInt(4));
        spnCar.setSelection(quote.getCar() - 1);
        quote.setRemainingAmount(cursor.getDouble(5));
        quote.setPaymentTerms(cursor.getInt(6));
        quote.setInterestRate(cursor.getDouble(7));

        malaPractica = quote.getRemainingAmount();

        try {
            date = new SimpleDateFormat("dd/mm/yy").parse(quote.getDate());
            interest = quote.getInterestRate() * quote.getPaymentTerms() / 100;
            accInterest = quote.getInterestRate() / 100;
            capital = quote.getRemainingAmount() / quote.getPaymentTerms();
            acc2 = quote.getRemainingAmount() - capital;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 1; i <= quote.getPaymentTerms(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));

            acc = capital * interest;

            for (int j = 1; j < 8; j++) {
                TextView textView = new TextView(this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setWidth(275);
                textView.setGravity(Gravity.CENTER);

                switch (j) {
                    case 1:
                        textView.setText("" + i);//print loop
                        break;
                    case 2:
                        textView.setText(addMonth(date, i));
                        break;
                    case 3:
                        textView.setText("    Pago    ");
                        break;
                    case 4:
                        textView.setText(df.format(capital));
                        toCapital += capital;
                        break;
                    case 5:
                        textView.setText(df.format(acc));
                        toInterest += acc;
                        break;
                    case 6:
                        textView.setText(df.format(capital + acc));
                        payToBank += (capital + acc);
                        break;
                    case 7:
                        textView.setText(df.format(acc2));
                        break;
                }
                tableRow.addView(textView);
            }
            acc2 -= capital;
            interest -= accInterest;
            tableItem.addView(tableRow);

            txtToCapital.setText("" +toCapital);
            txtToInterest.setText("" +toInterest);
            txtPayToBank.setText("" +payToBank);
        }
    }

    private String getText(double value) {

        return "";
    }

    private String addMonth(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);

        Date date2 = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date2);
    }

    private void save() {
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

        db.execSQL("UPDATE quote SET " +
                "date = \"" + quote.getDate() + "\"," +
                "vendor = " + quote.getVendor() + "," +
                "customer = " + quote.getCustomer() + "," +
                "car = " + quote.getCar() + "," +
                "downpayment = " + quote.getRemainingAmount() + "," +
                "paymentTerms = " + quote.getPaymentTerms() + "," +
                "interestRate = " + quote.getInterestRate() + "" +
                " WHERE id = \"" + quote.getId() + "\";"
        );
        finish();

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
}
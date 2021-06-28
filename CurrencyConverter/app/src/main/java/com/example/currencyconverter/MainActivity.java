package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    final String API_URL = "https://api.cambio.today/v1/quotes/MXN/";
    ImageButton btnUSD;
    ImageButton btnEUR;
    ImageButton btnARS;
    TextView txtAmt;
    TextView txtAmtConverted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();
    }

    private void Initialize() {
        btnUSD = this.findViewById(R.id.btnUSD);
        btnEUR = this.findViewById(R.id.btnEUR);
        btnARS = this.findViewById(R.id.btnARS);
        txtAmt = this.findViewById(R.id.txtAmt);
        txtAmtConverted = this.findViewById(R.id.txtAmtConverted);
        setListeners();
    }

    private void setListeners() {
        btnUSD.setOnClickListener(v -> {
            calculateExcAmt("USD");
        });
        btnEUR.setOnClickListener(v -> {
            calculateExcAmt("EUR");
        });
        btnARS.setOnClickListener(v -> {
            calculateExcAmt("ARS");
        });
    }

    private void calculateExcAmt(String toCurrency) {
        String amount = txtAmt.getText().toString();

        if(amount.isEmpty()){
            Toast.makeText(getBaseContext(), "Inserte una cantidad válida.", Toast.LENGTH_LONG).show();
            return;
        }

        RequestQueue rq = Volley.newRequestQueue(this);
        String url = API_URL + toCurrency + "/json?quantity=" + amount
                + "&key=8951|Z3TMBm0_U59*50mAcANq6zddBpLKjW7A";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jp = new JsonParser();
                JsonObject json = null;
                json = (JsonObject) jp.parse(response);

                float precioDolar = json.get("result").getAsJsonObject().get("amount").getAsFloat();
                txtAmtConverted.setText("" + precioDolar);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        StringRequest sr = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        rq.add(sr);
    }
}
package com.example.chinesecalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnRata;
    ImageButton btnBuey;
    ImageButton btnTigre;
    ImageButton btnConejo;
    ImageButton btnDragon;
    ImageButton btnSerpiente;
    ImageButton btnCaballo;
    ImageButton btnCabra;
    ImageButton btnMono;
    ImageButton btnGallo;
    ImageButton btnPerro;
    ImageButton btnCerdo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnRata = this.findViewById(R.id.btnRata);
        btnBuey = this.findViewById(R.id.btnBuey);
        btnTigre = this.findViewById(R.id.btnTigre);
        btnConejo = this.findViewById(R.id.btnConejo);
        btnDragon = this.findViewById(R.id.btnDragon);
        btnSerpiente = this.findViewById(R.id.btnSerpiente);
        btnCaballo = this.findViewById(R.id.btnCaballo);
        btnCabra = this.findViewById(R.id.btnCabra);
        btnMono = this.findViewById(R.id.btnMono);
        btnGallo = this.findViewById(R.id.btnGallo);
        btnPerro = this.findViewById(R.id.btnPerro);
        btnCerdo = this.findViewById(R.id.btnCerdo);


        setListeners();
    }

    private void setListeners() {
        btnRata.setOnClickListener(v -> {
            displayNewIntent("Rata");
        });
        btnBuey.setOnClickListener(v -> {
            displayNewIntent("Buey");
        });
        btnTigre.setOnClickListener(v -> {
            displayNewIntent("Tigre");
        });
        btnConejo.setOnClickListener(v -> {
            displayNewIntent("Conejo");
        });
        btnDragon.setOnClickListener(v -> {
            displayNewIntent("Dragon");
        });
        btnSerpiente.setOnClickListener(v -> {
            displayNewIntent("Serpiente");
        });
        btnCaballo.setOnClickListener(v -> {
            displayNewIntent("Caballo");
        });
        btnCabra.setOnClickListener(v -> {
            displayNewIntent("Cabra");
        });
        btnMono.setOnClickListener(v -> {
            displayNewIntent("Mono");
        });
        btnGallo.setOnClickListener(v -> {
            displayNewIntent("Gallo");
        });
        btnPerro.setOnClickListener(v -> {
            displayNewIntent("Perro");
        });
        btnCerdo.setOnClickListener(v -> {
            displayNewIntent("Cerdo");
        });
    }

    private void displayNewIntent(String titulo) {
        int drawableImgId = this.getResources().getIdentifier(titulo.toLowerCase(), "mipmap", this.getPackageName());
        String message = getExtraInfo(titulo);

        Intent intent = new Intent(this, DetailedActivity.class);

        intent.putExtra("titulo", titulo.toUpperCase());
        intent.putExtra("drawableImgId", drawableImgId);
        intent.putExtra("message", message);
        startActivity(intent);
    }

    private String getExtraInfo(String animalName) {
        Foo foo = new Foo(animalName.toLowerCase());
        Thread thread = new Thread(foo);
        thread.start();
        try {
            thread.join();

            return foo.getValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
package com.example.calculadoraimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnCalcular = (Button) this.findViewById(R.id.btnCalcular);
        final EditText txtAltura = (EditText) this.findViewById(R.id.txtAltura);
        final EditText txtPeso = (EditText) this.findViewById(R.id.txtPeso);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAltura = txtAltura.getText().toString();
                String sPeso = txtPeso.getText().toString();
                float altura = Float.parseFloat(sAltura);
                float peso = Float.parseFloat(sPeso);

                calcularIMC(peso, altura);
            }
        });
    }

    private void calcularIMC(float peso, float altura) {
        float imc = peso / (altura * altura);
        String clasificacion = "";
        String mensaje = "";
        if (imc < 18) {
            clasificacion = "Peso bajo. Necesario valorar signos de desnutrición";
        } else if (imc >= 18 && imc <= 24.0) {
            clasificacion = "Normal";
        } else if (imc >= 25 && imc <= 26.9) {
            clasificacion = "Sobrepeso";
        } else if (imc >= 27 && imc <= 29.9) {
            clasificacion = "Obesidad grado I.";
        } else if (imc >= 30 && imc <= 39.9) {
            clasificacion = "Obesidad grado II.";
        } else if (imc >= 40) {
            clasificacion = "Obesidad grado III.";
        }
        mensaje = " IMC: " + String.valueOf(imc) + " Clasificación: " + clasificacion;
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.show();
    }
}
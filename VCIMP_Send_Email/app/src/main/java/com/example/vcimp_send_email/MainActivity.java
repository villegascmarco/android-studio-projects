package com.example.vcimp_send_email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnEnviar;
    TextView txtDestinatario;
    TextView txtAsunto;
    TextView txtMensaje;
    CheckBox chbIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnEnviar = this.findViewById(R.id.btnEnviar);
        txtDestinatario = this.findViewById(R.id.txtDestinatario);
        txtAsunto = this.findViewById(R.id.txtAsunto);
        txtMensaje = this.findViewById(R.id.txtMensaje);
        chbIcono = this.findViewById(R.id.chbIcono);

        setListeners();
    }

    private void setListeners() {
        btnEnviar.setOnClickListener(e -> {
            sendEmail();
        });
    }

    private void sendEmail() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{txtDestinatario.getText().toString()});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, txtAsunto.getText().toString());
        intent.putExtra(android.content.Intent.EXTRA_TEXT, txtMensaje.getText());
        if (chbIcono.isChecked()) {
            System.out.println(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.mipmap.ic_launcher)
                    + '/' + getResources().getResourceTypeName(R.mipmap.ic_launcher)
                    + '/' + getResources().getResourceEntryName(R.mipmap.ic_launcher));
            Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.mipmap.ic_launcher)
                    + '/' + getResources().getResourceTypeName(R.mipmap.ic_launcher)
                    + '/' + getResources().getResourceEntryName(R.mipmap.ic_launcher)+".png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/png");
        }
        startActivity(intent);
    }
}
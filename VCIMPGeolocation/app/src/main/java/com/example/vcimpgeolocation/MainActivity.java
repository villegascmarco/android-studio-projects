package com.example.vcimpgeolocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnActivate;
    private Button btnDeactivate;
    private TextView txtPrecision;
    private TextView txtLongitud;
    private TextView txtLatitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        btnActivate = findViewById(R.id.btnActivate);
        btnDeactivate = findViewById(R.id.btnDeactivate);
        txtPrecision = findViewById(R.id.txtPrecision);
        txtLongitud = findViewById(R.id.txtLongitud);
        txtLatitude = findViewById(R.id.txtLatitude);

        setListeners();
    }

    private void setListeners() {
        btnActivate.setOnClickListener(v -> {
            activate();
        });
        btnDeactivate.setOnClickListener(v -> {
            deactivate();
        });
    }

    private void activate() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        showPosition(loc);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                showPosition(location);
            }

            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 100, 0, locationListener);
    }

    private void deactivate() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
            txtLatitude.setText("");
            txtLongitud.setText("");
            txtPrecision.setText("");
        }
    }

    private void showPosition(Location location) {
        if (location != null) {
            txtLatitude.setText(String.valueOf(location.getLatitude()));
            txtLongitud.setText(String.valueOf(location.getLongitude()));
            txtPrecision.setText(String.valueOf(location.getAccuracy()));
        } else {
            txtLatitude.setText("Sin_datos.");
            txtLongitud.setText("Sin_datos.");
            txtPrecision.setText("Sin_datos.");
        }
    }
}
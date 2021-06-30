package com.example.vcimpsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView txtOrientation;
    private TextView txtAccelerometer;
    private TextView txtMagnetic_field;
    private TextView txtTemperature;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        setListeners(Sensor.TYPE_ORIENTATION);
        setListeners(Sensor.TYPE_ACCELEROMETER);
        setListeners(Sensor.TYPE_MAGNETIC_FIELD);
        setListeners(Sensor.TYPE_TEMPERATURE);
    }

    private void initialize() {
        txtOrientation = findViewById(R.id.txtOrientation);
        txtAccelerometer = findViewById(R.id.txtAccelerometer);
        txtMagnetic_field = findViewById(R.id.txtMagnetic_field);
        txtTemperature = findViewById(R.id.txtTemperature);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);
    }

    private void setListeners(int sensorType) {
        List<Sensor> sensors = sensorManager.getSensorList(sensorType);
        if (!sensors.isEmpty()) {
            Sensor orientationSensor = sensors.get(0);
            sensorManager.registerListener(this, orientationSensor,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    for (int i = 0; i < sensorEvent.values.length; i++) {
                        updateText(txtOrientation, i + ": " + sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < sensorEvent.values.length; i++) {
                        updateText(txtAccelerometer, i + ": " + sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0; i < sensorEvent.values.length; i++) {
                        updateText(txtMagnetic_field, i + ": " + sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    for (int i = 0; i < sensorEvent.values.length; i++) {
                        updateText(txtTemperature, i + ": " + sensorEvent.values[i]);
                    }
                    break;
            }
        }
    }

    private void updateText(TextView textView, String value) {
        textView.setText(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
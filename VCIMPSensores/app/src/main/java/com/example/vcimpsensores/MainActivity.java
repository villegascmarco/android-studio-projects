package com.example.vcimpsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = (TextView) findViewById(R.id.salida);
        SensorManager sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.
                getSensorList(Sensor.TYPE_ALL);
        /*
        for(Sensor sensor: listaSensores) {
            log(sensor.getName());
        }

         */

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listaSensores.isEmpty()) {
            Sensor orientationSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, orientationSensor,
                    SensorManager.SENSOR_DELAY_UI);}
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listaSensores.isEmpty()) {
            Sensor acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, acelerometerSensor,
                    SensorManager.SENSOR_DELAY_UI);}
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listaSensores.isEmpty()) {
            Sensor orientationSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, orientationSensor,
                    SensorManager.SENSOR_DELAY_UI);}
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listaSensores.isEmpty()) {
            Sensor acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, acelerometerSensor,
                    SensorManager.SENSOR_DELAY_UI);}
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
    }
    private void log(String string) {
        salida.append(string + "\n");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Cada sensor puede provocar que un thread principal pase por aquí
//así que sincronizamos el acceso (se verá más adelante)
        synchronized (this) {
            switch(sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    for (int i=0 ; i<3 ; i++) {
                        log("Orientación "+i+": "+sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:for (int i=0 ; i<3 ; i++) {
                    log("Acelerómetro "+i+": "+sensorEvent.values[i]);
                }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i=0 ; i<3 ; i++) {
                        log("Magnetismo "+i+": "+sensorEvent.values[i]);
                    }
                    break;
                default:
                    for (int i=0 ; i<sensorEvent.values.length ; i++) {
                        log("Temperatura "+i+": "+sensorEvent.values[i]);
                    }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
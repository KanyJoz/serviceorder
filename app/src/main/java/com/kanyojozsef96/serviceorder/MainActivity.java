package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String LOG_TAG = MainActivity.class.getName();

    private Sensor sensor;
    private SensorManager sensorManager;
    private boolean isRunning = false;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void goToServiceOrderCreation(View view) {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    public void goToListServiceOrders(View view) {
        Intent intent = new Intent(this, ServiceOrderListActivity.class);
        intent.putExtra("key", 1);
        startActivity(intent);
    }

    public void goToListCancelledServiceOrders(View view) {
        Intent intent = new Intent(this, ServiceOrderListActivity.class);
        intent.putExtra("key", 2);
        startActivity(intent);
    }

    public void goToListCompletedServiceOrders(View view) {
        Intent intent = new Intent(this, ServiceOrderListActivity.class);
        intent.putExtra("key", 3);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.values[0] > 40 && !isRunning) {
            isRunning = true;
            mp = MediaPlayer.create(MainActivity.this, R.raw.bgmusic);
            mp.start();
        } else if(sensorEvent.values[0] < 40) {
            mp.stop();
            isRunning = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
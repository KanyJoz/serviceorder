package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToServiceOrderCreation(View view) {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    public void goToListServiceOrders(View view) {
        Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
    }

    public void goToListPriorityServiceOrders(View view) {
    }

    public void goToList5ServiceOrders(View view) {
    }
}
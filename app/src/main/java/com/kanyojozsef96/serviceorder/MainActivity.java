package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
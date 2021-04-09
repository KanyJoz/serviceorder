package com.kanyojozsef96.serviceorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ServiceOrderListActivity extends AppCompatActivity {
    private static final String LOG_TAG = ServiceOrderListActivity.class.getName();

    // Recycler View
    private int gridNumber = 1;
    private RecyclerView mRecyclerView;
    private ArrayList<ServiceOrder> mItemsData;
    private ServiceOrderListAdapter mAdapter;

    // FireStore
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    // NotificationHelper
    private NotificationHelper mNotificationHelper;

    // Life Hooks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order_list);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemsData = new ArrayList<>();
        mAdapter = new ServiceOrderListAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);

        mNotificationHelper = new NotificationHelper(this);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("service_orders");
        queryData();
    }

    // Menu
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.service_order_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBack:
                Log.d(LOG_TAG, "Back clicked!");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Firestore methods
    private void queryData() {
        mItemsData.clear();

        switch (getIntent().getIntExtra("key", 1)) {
            case 1:
                mItems.get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                ServiceOrder item = document.toObject(ServiceOrder.class);
                                item.setId(document.getId());
                                mItemsData.add(item);
                            }

                            // Notify the adapter of the change.
                            mAdapter.notifyDataSetChanged();
                        });

                break;
            case 2:
                mItems.whereEqualTo("state", "Cancelled").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                ServiceOrder item = document.toObject(ServiceOrder.class);
                                item.setId(document.getId());
                                mItemsData.add(item);
                            }

                            // Notify the adapter of the change.
                            mAdapter.notifyDataSetChanged();
                        });

                break;
            case 3:
                mItems.whereEqualTo("state", "Completed").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                ServiceOrder item = document.toObject(ServiceOrder.class);
                                item.setId(document.getId());
                                mItemsData.add(item);
                            }

                            // Notify the adapter of the change.
                            mAdapter.notifyDataSetChanged();
                        });

                break;
        }
    }

    public void modifyItem(ServiceOrder currentItem) {
        Intent intent = new Intent(this, ModificationActivity.class);
        intent.putExtra("itemId", currentItem._getId());
        intent.putExtra("itemCat", currentItem.getCategory());
        intent.putExtra("itemDesc", currentItem.getDescription());
        intent.putExtra("itemCon", currentItem.getContact());
        intent.putExtra("itemState", currentItem.getState());
        intent.putExtra("itemPrior", currentItem.getPriority());
        intent.putExtra("itemItems", currentItem._getItemsString());
        intent.putExtra("itemParties", currentItem._getPartiesString());
        intent.putExtra("itemNotes", currentItem._getNotesString());
        intent.putExtra("itemODate", currentItem.getOrderDate());
        intent.putExtra("itemEDate", currentItem.getExpectedDate());
        intent.putExtra("itemCDate", currentItem.getCancellationDate());
        intent.putExtra("itemCReason", currentItem.getCancellationReason());
        startActivity(intent);
    }

    public void cancelItem(ServiceOrder currentItem) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        EditText userInput = (EditText) promptsView
                .findViewById(R.id.popupText);

        // set dialog message
        alertDialogBuilder
                .setPositiveButton("Ready",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                currentItem.setCancellationReason(userInput.getText().toString());
                                dialog.cancel();
                            }
                        }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                String cancelD = getOrderDayFormatted();
                currentItem.setCancellationDate(cancelD);
                currentItem.setState("Cancelled");

                mFirestore.collection("service_orders").document(currentItem._getId())
                        .set(currentItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(LOG_TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(LOG_TAG, "Error writing document", e);
                            }
                        });
            }
        });
        alertDialog.show();
        mNotificationHelper.send(currentItem.getCategory());
    }

    public void deleteItem(ServiceOrder currentItem) {
        DocumentReference ref = mItems.document(currentItem._getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Log.d(LOG_TAG, "Item is successfully deleted: " + currentItem._getId());
                })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Item " + currentItem._getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
                });

        queryData();
        mNotificationHelper.cancel();
    }

    // Helper methods
    private String getOrderDayFormatted() {
        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }
}
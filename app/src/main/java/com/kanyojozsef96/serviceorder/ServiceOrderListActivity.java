package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

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

    // Life Hooks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_order_list);

        mRecyclerView = findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        // Initialize the ArrayList that will contain the data.
        mItemsData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ServiceOrderListAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);

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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Firestore methods
    private void queryData() {
        mItemsData.clear();
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
    }

    public void modifyItem(ServiceOrder currentItem) {
    }

    public void cancelItem(ServiceOrder currentItem) {
    }

    public void deleteItem(ServiceOrder currentItem) {
    }
}
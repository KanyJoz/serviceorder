package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ModificationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = ModificationActivity.class.getName();

    private EditText category;
    private EditText description;
    private EditText contactEmail;
    private Spinner stateSpinner;
    private RadioGroup priorityGroup;
    private EditText itemList;
    private EditText partyList;
    private EditText noteList;

    // Life hooks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);

        // UI
        category = findViewById(R.id.modCategory);
        description = findViewById(R.id.modDescription);
        contactEmail = findViewById(R.id.modContactEmail);
        stateSpinner = findViewById(R.id.modStateSpinner);
        priorityGroup = findViewById(R.id.modPriorityRadio);
        itemList = findViewById(R.id.modOrderItemList);
        partyList = findViewById(R.id.modOrderPartyList);
        noteList = findViewById(R.id.modServiceNotesList);
        priorityGroup.check(R.id.modPriorityTop);

        // Spinner config
        stateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        // current item UI settings
        category.setText(getIntent().getStringExtra("itemCat"));
        description.setText(getIntent().getStringExtra("itemDesc"));
        contactEmail.setText(getIntent().getStringExtra("itemCon"));

        int pos = 0;
        switch (getIntent().getStringExtra("itemState")) {
            case "Acknowledged": pos = 0; break;
            case "Rejected": pos = 1; break;
            case "Pending": pos = 2; break;
            case "Held": pos = 3; break;
            case "In Progress": pos = 4; break;
            case "Cancelled": pos = 5; break;
            case "Completed": pos = 6; break;
            case "Failed": pos = 7; break;
            case "Partial": pos = 8; break;
            case "Assessing Cancellation": pos = 9; break;
            case "Pending Cancellation": pos = 10; break;
            default: break;
        }

        int id = 0;
        switch (getIntent().getStringExtra("itemPrior")) {
            case "Top Priority": id = R.id.modPriorityTop; break;
            case "Medium Priority": id = R.id.modPriorityMedium; break;
            case "Low Priority": id = R.id.modPriorityLow; break;
            default: break;
        }

        stateSpinner.setSelection(pos);
        priorityGroup.check(id);
        itemList.setText(getIntent().getStringExtra("itemItems"));
        partyList.setText(getIntent().getStringExtra("itemParties"));
        noteList.setText(getIntent().getStringExtra("itemNotes"));
    }

    // Button listeners
    public void modifyServiceOrder(View view) {
        // Validation
        if(category.getText().toString().equals("") || description.getText().toString().equals("") || contactEmail.getText().toString().equals("")
                || itemList.getText().toString().equals("") || partyList.getText().toString().equals("") || noteList.getText().toString().equals("")){
            Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Please fill in all the fields!");
            return;
        }

        try {
            ServiceOrder serviceOrder = new ModifyAsyncTask(category, description, contactEmail, stateSpinner,
                    priorityGroup, itemList, partyList, noteList,
                        getIntent().getStringExtra("itemODate"), getIntent().getStringExtra("itemEDate"),
                        getIntent().getStringExtra("itemCDate"), getIntent().getStringExtra("itemCReason"),
                            getIntent().getStringExtra("itemId")).execute().get();

            Intent intent = new Intent(this, ServiceOrderListActivity.class);
            startActivity(intent);

            if(serviceOrder._getId() != null) {
                Toast.makeText(this, "DocumentSnapshot rewritten with ID: " + serviceOrder._getId(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelServiceOrder(View view) {
        finish();
    }

    // Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

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


public class CreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = CreationActivity.class.getName();

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
        setContentView(R.layout.activity_creation);

        // UI
        category = findViewById(R.id.createCategory);
        description = findViewById(R.id.createDescription);
        contactEmail = findViewById(R.id.createContactEmail);
        stateSpinner = findViewById(R.id.createStateSpinner);
        priorityGroup = findViewById(R.id.createPriorityRadio);
        itemList = findViewById(R.id.createOrderItemList);
        partyList = findViewById(R.id.createOrderPartyList);
        noteList = findViewById(R.id.createServiceNotesList);
        priorityGroup.check(R.id.createPriorityTop);

        // Spinner config
        stateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
    }

    // Button listeners
    public void createServiceOrder(View view) {
        // Validation
        if(category.getText().toString().equals("") || description.getText().toString().equals("") || contactEmail.getText().toString().equals("")
                || itemList.getText().toString().equals("") || partyList.getText().toString().equals("") || noteList.getText().toString().equals("")){
            Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Please fill in all the fields!");
            return;
        }

        try {
            ServiceOrder serviceOrder = new CreateAsyncTask(category, description, contactEmail, stateSpinner,
                    priorityGroup, itemList, partyList, noteList).execute().get();

            if(serviceOrder._getId() != null) {
                Toast.makeText(this, "DocumentSnapshot written with ID: " + serviceOrder._getId(), Toast.LENGTH_SHORT).show();
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
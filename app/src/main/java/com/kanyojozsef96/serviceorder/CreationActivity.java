package com.kanyojozsef96.serviceorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        stateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_labels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
    }



    public void createServiceOrder(View view) {
        String cat = category.getText().toString();
        String desc = description.getText().toString();
        String contact = contactEmail.getText().toString();
        String stateType = stateSpinner.getSelectedItem().toString();

        int priorityTypeId = priorityGroup.getCheckedRadioButtonId();
        View radioButton = priorityGroup.findViewById(priorityTypeId);
        int id = priorityGroup.indexOfChild(radioButton);
        String priorityType =  ((RadioButton)priorityGroup.getChildAt(id)).getText().toString();

        String items = itemList.getText().toString();
        String parties = partyList.getText().toString();
        String notes = noteList.getText().toString();

        Log.i(LOG_TAG, "DATA");

        Log.i(LOG_TAG, cat);
        Log.i(LOG_TAG, desc);
        Log.i(LOG_TAG, contact);
        Log.i(LOG_TAG, stateType);

        Log.i(LOG_TAG, priorityType);

        Log.i(LOG_TAG, Arrays.toString(items.split("\n"))); // Arrays.asList()
        Log.i(LOG_TAG, parties);
        Log.i(LOG_TAG, notes);


        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.i(LOG_TAG, formattedDate);

        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date c2 = calendar.getTime();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate2 = df.format(c2);
        Log.i(LOG_TAG, formattedDate2);
    }

    public void cancelServiceOrder(View view) {
        finish();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
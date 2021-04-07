package com.kanyojozsef96.serviceorder;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateAsyncTask extends AsyncTask<Void, Void, ServiceOrder> {
    private static final String LOG_TAG = CreateAsyncTask.class.getName();

    private FirebaseFirestore db;

    private final WeakReference<EditText> category;
    private final WeakReference<EditText> description;
    private final WeakReference<EditText> contactEmail;
    private final WeakReference<Spinner> stateSpinner;
    private final WeakReference<RadioGroup> priorityGroup;
    private final WeakReference<EditText> itemList;
    private final WeakReference<EditText> partyList;
    private final WeakReference<EditText> noteList;

    @SuppressWarnings("deprecation")
    public CreateAsyncTask (EditText c, EditText d, EditText con, Spinner st, RadioGroup pri, EditText item, EditText party, EditText note) {
        category = new WeakReference<>(c);
        description = new WeakReference<>(d);
        contactEmail = new WeakReference<>(con);
        stateSpinner = new WeakReference<>(st);
        priorityGroup = new WeakReference<>(pri);
        itemList = new WeakReference<>(item);
        partyList = new WeakReference<>(party);
        noteList = new WeakReference<>(note);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected ServiceOrder doInBackground(Void... voids) {
        // Getting Admin input
        String cat = category.get().getText().toString();
        String desc = description.get().getText().toString();
        String contact = contactEmail.get().getText().toString();
        String stateType = stateSpinner.get().getSelectedItem().toString();

        int priorityTypeId = priorityGroup.get().getCheckedRadioButtonId();
        View radioButton = priorityGroup.get().findViewById(priorityTypeId);
        int id = priorityGroup.get().indexOfChild(radioButton);
        String priorityType =  ((RadioButton)priorityGroup.get().getChildAt(id)).getText().toString();

        String items = itemList.get().getText().toString();
        String parties = partyList.get().getText().toString();
        String notes = noteList.get().getText().toString();

        String orderD = getOrderDayFormatted();
        String expectedD = getExpectedDayFormatted(priorityType);

        // TEST
        Log.i(LOG_TAG, "DATA");
        Log.i(LOG_TAG, cat);
        Log.i(LOG_TAG, desc);
        Log.i(LOG_TAG, contact);
        Log.i(LOG_TAG, stateType);
        Log.i(LOG_TAG, priorityType);
        Log.i(LOG_TAG, items); // Arrays.asList()
        Log.i(LOG_TAG, parties);
        Log.i(LOG_TAG, notes);
        Log.i(LOG_TAG, orderD);
        Log.i(LOG_TAG, expectedD);

        // Creating Bean
        ServiceOrder orderToAdd = new ServiceOrder();
        orderToAdd.setCategory(cat);
        orderToAdd.setDescription(desc);
        orderToAdd.setContact(contact);
        orderToAdd.setState(stateType);
        orderToAdd.setPriority(priorityType);
        orderToAdd.setItems(Arrays.asList(items.split("\n")));
        orderToAdd.setParties(Arrays.asList(parties.split("\n")));
        orderToAdd.setNotes(Arrays.asList(notes.split("\n")));
        orderToAdd.setOrderDate(orderD);
        orderToAdd.setExpectedDate(expectedD);

        // Adding to FireStore
        db.collection("service_orders")
                .add(orderToAdd)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(LOG_TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        orderToAdd.setId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error adding document", e);
                    }
                });

        // Cleaning input fields
        category.get().setText("");
        description.get().setText("");
        contactEmail.get().setText("");
        itemList.get().setText("");
        partyList.get().setText("");
        noteList.get().setText("");

        // Return Bean
        return orderToAdd;
    }

    // Helper methods
    private String getOrderDayFormatted() {
        Calendar calendar = Calendar.getInstance();
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    private String getExpectedDayFormatted(String priority) {
        Calendar calendar = Calendar.getInstance();

        switch (priority) {
            case "Top Priority":
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case "Medium Priority":
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                break;
            case "Low Priority":
                calendar.add(Calendar.DAY_OF_MONTH, 21);
                break;
            default:
                calendar.add(Calendar.DAY_OF_MONTH, 18);
                break;
        }

        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

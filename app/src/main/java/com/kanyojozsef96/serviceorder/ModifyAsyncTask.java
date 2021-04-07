package com.kanyojozsef96.serviceorder;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ModifyAsyncTask extends AsyncTask<Void, Void, ServiceOrder> {
    private static final String LOG_TAG = ModifyAsyncTask.class.getName();

    private FirebaseFirestore db;

    private final WeakReference<EditText> category;
    private final WeakReference<EditText> description;
    private final WeakReference<EditText> contactEmail;
    private final WeakReference<Spinner> stateSpinner;
    private final WeakReference<RadioGroup> priorityGroup;
    private final WeakReference<EditText> itemList;
    private final WeakReference<EditText> partyList;
    private final WeakReference<EditText> noteList;

    private final String orderDate;
    private final String expectedDate;
    private final String cancellationDate;
    private final String cancellationReason;
    private final String itemID;

    @SuppressWarnings("deprecation")
    public ModifyAsyncTask (EditText c, EditText d, EditText con, Spinner st, RadioGroup pri, EditText item, EditText party, EditText note, String oD, String eD, String cD, String cR, String id) {
        category = new WeakReference<>(c);
        description = new WeakReference<>(d);
        contactEmail = new WeakReference<>(con);
        stateSpinner = new WeakReference<>(st);
        priorityGroup = new WeakReference<>(pri);
        itemList = new WeakReference<>(item);
        partyList = new WeakReference<>(party);
        noteList = new WeakReference<>(note);

        orderDate = oD;
        expectedDate = eD;
        cancellationDate = cD;
        cancellationReason = cR;
        itemID = id;

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected ServiceOrder doInBackground(Void... voids) {
        // Getting Admin input
        String ID = itemID;

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

        String orderD = orderDate;
        String expectedD = expectedDate;
        String cancelD = cancellationDate;
        String cancelR = cancellationReason;

        // Creating Bean
        ServiceOrder orderToAdd = new ServiceOrder();
        orderToAdd.setId(ID);
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
        orderToAdd.setCancellationDate(cancelD);
        orderToAdd.setCancellationReason(cancelR);

        // Setting to FireStore
        db.collection("service_orders").document(ID)
                .set(orderToAdd)
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

        // Return Bean
        return orderToAdd;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}

package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().getName();
    private static final String SECRET_KEY = "444";

    private EditText userName;
    private EditText userEmail;
    private EditText userPwd;
    private EditText userPwdAgain;

    private SharedPreferences preferences;

    private EditText userPhone;
    private Spinner spinner;
    private EditText addressArea;
    private RadioGroup rGroup;

    // Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bundle bundle = getIntent().getExtras();
        // int secretKey = bundle.getInt("SECRET_KEY");
        int secretKey = getIntent().getIntExtra("SECRET_KEY", -1);
        if(secretKey != 444) {
            finish();
        }

        // globális változók beállítása
        userName = findViewById(R.id.regUserName);
        userEmail = findViewById(R.id.regEmail);
        userPwd = findViewById(R.id.regPwd);
        userPwdAgain = findViewById(R.id.regPwdAgain);

        // spinner
        userPhone = findViewById(R.id.regPhone);
        spinner = findViewById(R.id.phoneSpinner);
        addressArea = findViewById(R.id.regAddress);
        rGroup = findViewById(R.id.regRadioGroup);
        rGroup.check(R.id.regRadioGuest); // default check

        // session infó átkérése
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String prefUserName = preferences.getString("userName", "");
        String prefPwd = preferences.getString("pwd", "");

        userName.setText(prefUserName);
        userPwd.setText(prefPwd);
        userPwdAgain.setText(prefPwd);

        // spinner feltöltés
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.phoneModes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String usr = userName.getText().toString();
        String pwd = userPwd.getText().toString();
        String pwdAgain = userPwdAgain.getText().toString();
        String email = userEmail.getText().toString();

        String phone = userPhone.getText().toString();
        String spinText = spinner.getSelectedItem().toString();
        String addressText = addressArea.getText().toString();

        if(!pwd.equals(pwdAgain)){
            Log.e(LOG_TAG, "Nem azonosak a jelszavak");
            return;
        }

        int checkedId = rGroup.getCheckedRadioButtonId();
        RadioButton radioButton = rGroup.findViewById(checkedId);
        String accType = radioButton.getText().toString();

        Log.i(LOG_TAG, "\nUsername: " + usr + "\nEmail: " + email + "\nPassword: " + pwd + "\nPassword Again: " + pwdAgain);

        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User created successfully");
                    startShopping();
                } else {
                    Log.d(LOG_TAG, "User creation failed");
                    Toast.makeText(RegisterActivity.this, "User creation failed! " + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void back(View view) {
        finish();
    }


    private void startShopping() {
        Intent intent = new Intent(this, ShopListActivity.class);
        // intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }


    // Életciklusok
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    // spinneres cucc
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }
}
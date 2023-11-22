package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Account;
import com.example.hotelbooking.model.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edtFullName, edtPhoneNumber, edtEmail, edtPassword, edtConfirmPassword;
    CheckBox termsCheckBox;
    MaterialButton registerButton;
    TextView returnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        mAuth = FirebaseAuth.getInstance();

        onLoginButtonClick();
        onRegisterButtonClick();


    }

    private void onLoginButtonClick(){
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void onRegisterButtonClick(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String fullName = edtFullName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                Account account = new Account(email, password);

                //check Empty
                if(TextUtils.isEmpty(fullName)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Fullname is not be empty !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                    return;
                }

                if(TextUtils.isEmpty(phoneNumber)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Phone number is not be empty !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Email is not be empty !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Password is not be empty !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                    return;
                }

                if(!confirmPassword.equals(password)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Confirm password is incorrect !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                    return;
                }

                if(!termsCheckBox.isChecked()){
                    FancyToast.makeText(getApplicationContext(),
                                    "You must agree with the Terms !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.WARNING,
                                    false)
                            .show();
                    return;
                }

                //Create User On Firebase Auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createNewClient: Successfull! ");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userID = user.getUid();
                                    Client client = new Client(userID, account, fullName, phoneNumber, null);

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("clients").child(userID);
                                    databaseReference.child("orders").setValue(null);
                                    databaseReference.setValue(client)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        FancyToast.makeText(getApplicationContext(),
                                                                        "Register Successful!",
                                                                        FancyToast.LENGTH_LONG,
                                                                        FancyToast.SUCCESS,
                                                                        false)
                                                                .show();
                                                        Log.d(TAG, "onComplete: "+userID);
                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    else{
                                                        FancyToast.makeText(getApplicationContext(),
                                                                        "Something went wrong!!!",
                                                                        FancyToast.LENGTH_LONG,
                                                                        FancyToast.ERROR,
                                                                        false)
                                                                .show();
                                                    }
                                                }
                                            });


                                } else {
                                    Log.d(TAG, "createNewClient: Failed! ");
                                }
                            }
                        });


            }
        });
    }

    public void initView(){
        edtFullName = findViewById(R.id.edt_fullname);
        edtPhoneNumber = findViewById(R.id.edt_phonenumber);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        termsCheckBox = findViewById(R.id.terms_checkbox);
        registerButton = findViewById(R.id.register_button);
        returnLogin = findViewById(R.id.return_login_button);
    }

}
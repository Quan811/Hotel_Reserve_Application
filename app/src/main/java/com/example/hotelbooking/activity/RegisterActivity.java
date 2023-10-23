package com.example.hotelbooking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
        setContentView(R.layout.register);

        initView();

        mAuth = FirebaseAuth.getInstance();

        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, fullName, phoneNumber, password, retypePassword;
                email = String.valueOf(edtEmail.getText()).trim();
                fullName = String.valueOf(edtFullName.getText()).trim();
                phoneNumber = String.valueOf(edtPhoneNumber.getText()).trim();
                password = String.valueOf(edtPassword.getText()).trim();
                retypePassword = String.valueOf(edtConfirmPassword.getText()).trim();

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

                if(!retypePassword.equals(password)){
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

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FancyToast.makeText(getApplicationContext(),
                                            "Account created !",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.SUCCESS,
                                            false)
                                            .show();

                                    registerButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    FancyToast.makeText(getApplicationContext(),
                                            "Create failed !",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.ERROR,
                                            false)
                                            .show();
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
package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.shashank.sony.fancytoastlib.FancyToast;


public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtEmail, edtPassword;
    TextView registerButton;
    MaterialButton loginButton;
    ImageButton loginWithGoogleButton, loginWithFacebookButton;
    FirebaseAuth mAuth;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();


        //register
        onRegisterButtonClick();

        //login with email and password
        onLoginButtonClick();

        //login with google
        onLoginGoogleButtonClick();



    }

    private void onLoginGoogleButtonClick(){
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        loginWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent, 1234);
            }
        });
    }

    private void onRegisterButtonClick(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void onLoginButtonClick(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(edtEmail.getText()).trim();
                password = String.valueOf(edtPassword.getText()).trim();

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

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(email.equals("admin@gmail.com")  && password.equals("admin1234")){
                                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(LoginActivity.this, ClientHomeActivity.class);
                                        startActivity(intent);
                                    }

                                    FancyToast.makeText(getApplicationContext(),
                                                    "Login Successful !",
                                                    FancyToast.LENGTH_LONG,
                                                    FancyToast.SUCCESS,
                                                    false)
                                            .show();

                                }else{
                                    FancyToast.makeText(getApplicationContext(),
                                                    "Authentication failed !",
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
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        loginWithGoogleButton = findViewById(R.id.login_with_google_button);
        loginWithFacebookButton = findViewById(R.id.login_with_facebook_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken()
                        , null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(LoginActivity.this,
                                            ClientHomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Login Successful",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            task.getException().getMessage(),
                                            Toast.LENGTH_SHORT)
                                            .show();

                                }
                            }
                        });
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),
                                "Error: " + e.getStatusCode(),
                                Toast.LENGTH_SHORT)
                                .show();
            }


        }
    }

}
package com.example.hotelbooking.activity;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hotelbooking.R;
import com.example.hotelbooking.fragment.AccountFragment;
import com.example.hotelbooking.fragment.HomeFragment;
import com.example.hotelbooking.fragment.MyReserveFragment;
import com.example.hotelbooking.model.Client;
import com.example.hotelbooking.model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class ClientHomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        initView();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        //bottom navigation item click
        bottomNavigationItemSelected();


    }

    public void initView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
    private void bottomNavigationItemSelected(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.tab_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.tab_my_reserve:
                        selectedFragment = new MyReserveFragment();
                        break;
                    case R.id.tab_account:
                        selectedFragment = new AccountFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
    }




}
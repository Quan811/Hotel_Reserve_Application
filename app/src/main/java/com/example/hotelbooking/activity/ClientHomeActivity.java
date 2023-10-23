package com.example.hotelbooking.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.hotelbooking.R;
import com.example.hotelbooking.fragment.AccountFragment;
import com.example.hotelbooking.fragment.BookingFragment;
import com.example.hotelbooking.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_home_activity);

        initView();

        //Bottom Navigation
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.tab_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.tab_booking:
                    selectedFragment = new BookingFragment();
                    break;
                case R.id.tab_account:
                    selectedFragment = new AccountFragment();
                    break;
            }

            if (selectedFragment != null) {
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        });
    }

    public void initView(){
        bottomNavigation = findViewById(R.id.navigation);
    }


}
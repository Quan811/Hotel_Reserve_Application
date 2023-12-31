package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.AdminAccommodationsAdapter;
import com.example.hotelbooking.adapter.AdminOrderAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Order;
import com.example.hotelbooking.viewmodel.AllAccommodationViewModel;
import com.example.hotelbooking.viewmodel.AllOrdersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {
    TextView seeAllAccommodations, seeAllOrders;
    RecyclerView rcvAccommodations, rcvOrders;
    AllAccommodationViewModel allAccommodationViewModel;
    AllOrdersViewModel allOrdersViewModel;
    MaterialButton buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        initView();

        setRcvAccommodations();
        setRcvOrders();

        seeAllAccommodationsOnClick();
        seeAllOrdersOnClick();

        buttonLogoutOnClick();
    }

    private void seeAllAccommodationsOnClick(){
        seeAllAccommodations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAccommodationsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void seeAllOrdersOnClick(){
        seeAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRcvAccommodations(){
        allAccommodationViewModel = new ViewModelProvider(this).get(AllAccommodationViewModel.class);
        allAccommodationViewModel.getAccommodationsLiveData().observe(this, new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> accommodations) {
                List<Accommodation> listDataReturn = new ArrayList<>();
                for(int i = 0; i<3; i++){
                    listDataReturn.add(accommodations.get(i));
                }

                if (listDataReturn.size() != 0) {
                    setDataRcvAccommodations(listDataReturn);
                }
            }
        });
    }
    private void setRcvOrders(){
        allOrdersViewModel = new ViewModelProvider(this).get(AllOrdersViewModel.class);
        allOrdersViewModel.getOrdersLiveData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if(orders.size() >=3){
                    List<Order> listOrderReturn = new ArrayList<>();
                    for(int i = 0 ; i<3; i++){
                        listOrderReturn.add(orders.get(i));
                    }
                    if(listOrderReturn != null){
                        Log.d(TAG, "setRcvOrders: list order not null ");
                        Log.d(TAG, "list order: "+listOrderReturn.size());
                        setDataRcvOrders(listOrderReturn);
                    }
                    else{
                        Log.d(TAG, "setRcvOrders: list order is null ");
                    }
                }
            }
        });
    }
    private void setDataRcvAccommodations(List<Accommodation> list){
        if(list != null){
            AdminAccommodationsAdapter adminAccommodationsAdapter = new AdminAccommodationsAdapter(getApplicationContext(), list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_left_to_right);
            rcvAccommodations.setLayoutAnimation(animation);
            adminAccommodationsAdapter.notifyDataSetChanged();
            rcvAccommodations.setLayoutManager(linearLayoutManager);
            rcvAccommodations.setAdapter(adminAccommodationsAdapter);
        }
        else{
            Log.d(TAG, "setDataRcvAccommodations: ko co du lieu");
        }
    }
    private void setDataRcvOrders(List<Order> list){
        if(list != null){
            AdminOrderAdapter adminOrderAdapter = new AdminOrderAdapter(getApplicationContext(), list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            rcvOrders.setLayoutManager(linearLayoutManager);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation_right_to_left);
            rcvOrders.setLayoutAnimation(animation);
            adminOrderAdapter.notifyDataSetChanged();
            rcvOrders.setAdapter(adminOrderAdapter);
        }
        else{
            Log.d(TAG, "setDataRcvOrders: ko co du lieu");
        }
    }

    private void buttonLogoutOnClick(){
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        seeAllAccommodations = findViewById(R.id.see_all_accommodations);
        seeAllOrders = findViewById(R.id.see_all_order);
        rcvAccommodations = findViewById(R.id.rcv_accommodations);
        rcvOrders = findViewById(R.id.rcv_orders);
        buttonLogout = findViewById(R.id.button_logout);
    }
}
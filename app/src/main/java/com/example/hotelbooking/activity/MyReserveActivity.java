package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.MyReserveAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Order;

import java.util.ArrayList;


public class MyReserveActivity extends AppCompatActivity {
    RecyclerView rcvMyReserve;
    ImageView buttonBack;
    MyReserveAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reserve);

        initView();

        setRcvMyReserve();
        onButtonBackClicked();

    }

    private void initView() {
        rcvMyReserve = findViewById(R.id.rcv_myreserve);
        buttonBack = findViewById(R.id.button_back);
    }
    private void onButtonBackClicked(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientHomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setRcvMyReserve(){
        Order order = getOrderFromIntent();
        if(order != null){
            Accommodation accommodation = order.getAccommodation();
            Log.d(TAG, "setRcvCategory: Co du lieu order de set cho rcv");
            Log.d(TAG, "client Name: "+order.getClientName());
            Log.d(TAG, "Accommodation Name: "+accommodation.getAccommodationName());
            Log.d(TAG, "Location: "+accommodation.getLocation());
            Log.d(TAG, "phone number: "+order.getClientPhoneNumber());
            Log.d(TAG, "time order: "+order.getTimeOrder());
            Log.d(TAG, "total: "+order.getTotalPayment());

            adapter = new MyReserveAdapter(getApplicationContext(), order);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL,false);
            rcvMyReserve.setLayoutManager(linearLayoutManager);
            rcvMyReserve.setAdapter(adapter);
        }
        else{
            Log.d(TAG, "setRcvCategory: Khong co du lieu order");
        }
    }

    private Order getOrderFromIntent() {
        Intent intent = getIntent();
        Order order = new Order();
        Accommodation accommodation = order.getAccommodation();
        if (intent != null){
            Log.d(TAG, "getOrderFromIntent: Co du lieu cua order");
            order = (Order) intent.getSerializableExtra("order_from_reserve");
        }
        else {
            Log.d(TAG, "getOrderFromIntent: khong co du lieu order");
        }
        return order;
    }


}
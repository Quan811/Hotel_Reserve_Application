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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.AdminOrderAdapter;
import com.example.hotelbooking.model.Order;
import com.example.hotelbooking.viewmodel.AllOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminOrdersActivity extends AppCompatActivity {
    RecyclerView rcvOrder;
    ImageView buttonBack;
    Spinner spinnerOrder;
    String spinnerSelected;
    AdminOrderAdapter adminOrderAdapter;
    AllOrdersViewModel ordersViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        initView();
        showAllOrder();
        setOrderSpinner();
        buttonBackOnClick();
    }

    private void initView() {
        rcvOrder = findViewById(R.id.rcv_orders);
        buttonBack = findViewById(R.id.button_back);
        spinnerOrder = findViewById(R.id.spinner_order);
    }

    private void showAllOrder(){
        ordersViewModel = new ViewModelProvider(this).get(AllOrdersViewModel.class);
        ordersViewModel.getOrdersLiveData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                setDataRcvOrder(orders);
            }
        });
    }

    private void setDataRcvOrder(List<Order> listOrder){
        if(listOrder != null){
            adminOrderAdapter = new AdminOrderAdapter(getApplicationContext(), listOrder);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_left_to_right);
            rcvOrder.setLayoutAnimation(animation);
            adminOrderAdapter.notifyDataSetChanged();
            rcvOrder.setLayoutManager(linearLayoutManager);
            rcvOrder.setAdapter(adminOrderAdapter);
        }
        else{
            Log.d(TAG, "setDataRcvAccommodations: ko co du lieu");
        }
    }

    private void setOrderSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_order, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerOrder.setAdapter(adapter);
        spinnerOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerSelected = spinnerOrder.getSelectedItem().toString();
                switch (spinnerSelected){
                    case "All Order":
                        showAllOrder();
                        break;
                    case "Processing":
                        showProcessingOrder();
                        break;
                    case "Approved":
                        showApprovedOrder();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có item nào được chọn (nếu cần)
            }
        });
    }
     private void showProcessingOrder(){
         ordersViewModel = new ViewModelProvider(this).get(AllOrdersViewModel.class);
         ordersViewModel.getOrdersLiveData().observe(this, new Observer<List<Order>>() {
             @Override
             public void onChanged(List<Order> orders) {
                 List<Order> listResult = new ArrayList<>();
                 for(Order order : orders){
                     if(order.getOrderStatus().equals("Processing")){
                         listResult.add(order);
                     }
                 }
                 if(listResult != null){
                     setDataRcvOrder(listResult);
                 }

             }
         });
     }

     private void showApprovedOrder(){
         ordersViewModel = new ViewModelProvider(this).get(AllOrdersViewModel.class);
         ordersViewModel.getOrdersLiveData().observe(this, new Observer<List<Order>>() {
             @Override
             public void onChanged(List<Order> orders) {
                 List<Order> listResult = new ArrayList<>();
                 for(Order order : orders){
                     if(order.getOrderStatus().equals("Approved")){
                         listResult.add(order);
                     }
                 }
                 if(listResult != null){
                     setDataRcvOrder(listResult);
                 }

             }
         });
     }

     private void buttonBackOnClick(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                startActivity(intent);
            }
        });
     }

}
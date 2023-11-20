package com.example.hotelbooking.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbooking.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientOrdersViewModel extends ViewModel {
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final DatabaseReference databaseReference = FirebaseDatabase.
            getInstance()
            .getReference("clients")
            .child(userID)
            .child("orders");

    public LiveData<List<Order>> getOrdersLiveData() {
        final MutableLiveData<List<Order>> orderLiveData = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                orderLiveData.setValue(orderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
        return orderLiveData;
    }
}

package com.example.hotelbooking.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbooking.model.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class ClientInfoViewModel extends ViewModel {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("clients");

    public LiveData<Client> getClientLiveData(){
        final MutableLiveData<Client> clientMutableLiveData = new MutableLiveData<>();
        String userID = FirebaseAuth.getInstance().getUid();
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client = snapshot.getValue(Client.class);
                if(client != null){
                    Log.d(TAG, "getClientLiveData: co du lieu client ");
                    clientMutableLiveData.setValue(client);
                }
                else {
                    Log.d(TAG, "getClientLiveData: khong co du lieu client ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "getClientLiveData:  Failed! ");
            }
        });
        return clientMutableLiveData;
    }
}

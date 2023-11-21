package com.example.hotelbooking.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.AdminAccommodationsAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AllAccommodationViewModel extends ViewModel {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("accommodations");

    public LiveData<List<Accommodation>> getAccommodationsLiveData() {
        final MutableLiveData<List<Accommodation>> accommodationsLiveData = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Accommodation> accommodations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Accommodation accommodation = snapshot.getValue(Accommodation.class);
                    if (accommodation != null) {
                        accommodations.add(accommodation);
                    }
                }
                accommodationsLiveData.setValue(accommodations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
        return accommodationsLiveData;
    }

}

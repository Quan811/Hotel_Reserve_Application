package com.example.hotelbooking.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.hotelbooking.model.Accommodation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AccommodationViewModel extends ViewModel {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("accommodations");

    public void getAllAccommodations(final OnDataLoadedListener onDataLoadedListener) {
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
                onDataLoadedListener.onDataLoaded(accommodations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDataLoadedListener.onError(databaseError.toException());
            }
        });
    }

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Accommodation> accommodations);

        void onError(Exception e);
    }
}

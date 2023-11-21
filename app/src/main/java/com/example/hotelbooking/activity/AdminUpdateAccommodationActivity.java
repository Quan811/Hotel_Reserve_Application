package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.AdminRoomAdapter;
import com.example.hotelbooking.adapter.ImageURLAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class AdminUpdateAccommodationActivity extends AppCompatActivity {
    TextInputEditText edtAccommodationName, edtLocation, edtDescription,
            edtAccommodationType, edtThumbnail, edtRating;
    MaterialButton buttonSave, buttonCancle, buttonAddImage;
    TextInputEditText edtAddImage;
    RecyclerView rcvURL, rcvRoom;
    ImageURLAdapter urlAdapter;
    AdminRoomAdapter adminRoomAdapter;
    List<String> listURL;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_accommodation);

        initView();
        setInitData();
        setRcvURL();
        setRcvRoom();
        addNewImage();

        buttonSaveOnClick();
        buttonCancleOnclick();

    }

    private Accommodation getAccommodation(){
        Accommodation accommodation = new Accommodation();
        Intent intent = getIntent();
        if(intent != null){
            Serializable serializable = intent.getSerializableExtra("accommodation_to_update_activity");
            if(serializable != null){
                accommodation = (Accommodation) serializable;
            }
        }
        return accommodation;
    }
    private void setInitData(){
        Accommodation accommodation = getAccommodation();
        if(accommodation != null){
            edtAccommodationName.setText(accommodation.getAccommodationName());
            edtLocation.setText(accommodation.getLocation());
            edtRating.setText(accommodation.getAccommodationRating());
            edtDescription.setText(accommodation.getAccommodationDescription());
            edtAccommodationType.setText(accommodation.getAccommodationType());
            edtThumbnail.setText(accommodation.getThumbnail());
        }

    }
    private void initView() {
        edtAccommodationName = findViewById(R.id.edt_accommodation_name);
        edtLocation = findViewById(R.id.edt_location);
        edtRating = findViewById(R.id.edt_rating);
        edtDescription = findViewById(R.id.edt_accommodation_des);
        edtAccommodationType = findViewById(R.id.edt_accommodation_type);
        edtThumbnail = findViewById(R.id.edt_thumbnail);
        buttonSave = findViewById(R.id.button_save);
        buttonCancle = findViewById(R.id.button_cancle);
        rcvURL = findViewById(R.id.rcv_url);
        rcvRoom = findViewById(R.id.rcv_room);
        buttonAddImage = findViewById(R.id.button_add_image);
        edtAddImage = findViewById(R.id.edt_add_image);

    }

    private void setRcvRoom(){
        Accommodation accommodation = getAccommodation();
        List<Room> listRoom = accommodation.getRoomList();
        adminRoomAdapter = new AdminRoomAdapter(getApplicationContext(), listRoom);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvRoom.setLayoutManager(layoutManager);
        rcvRoom.setAdapter(adminRoomAdapter);
    }

    private void setRcvURL(){
        Accommodation accommodation = getAccommodation();
        listURL = accommodation.getImgURL();
        urlAdapter = new ImageURLAdapter(getApplicationContext(), listURL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvURL.setLayoutManager(layoutManager);
        rcvURL.setAdapter(urlAdapter);
    }

    private void addNewImage(){
        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listURL.add(edtAddImage.getText().toString().trim());
                ImageURLAdapter urlAdapter = new ImageURLAdapter(getApplicationContext(), listURL);
                rcvURL.setAdapter(urlAdapter);
                edtAddImage.setText("");
            }
        });

    }


    private void buttonSaveOnClick(){
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Room> roomList = adminRoomAdapter.getListRoom();;
                List<String> urlList = urlAdapter.getListURL();

                String accommodationName = edtAccommodationName.getText().toString().trim();
                String rating = edtRating.getText().toString().trim();
                String accommodationDes = edtDescription.getText().toString().trim();
                String accommodationLocation = edtLocation.getText().toString().trim();
                String accommodationType = edtAccommodationType.getText().toString().trim();
                String thumbnail = edtThumbnail.getText().toString().trim();

                Accommodation accommodation = new Accommodation(
                        accommodationName, accommodationDes, rating,
                        accommodationType, thumbnail, urlList, accommodationLocation, roomList
                        );

                Gson gson = new Gson();
                String jsonString = gson.toJson(accommodation);
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> accommodationMap = gson.fromJson(jsonString, type);

                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("accommodations").child(accommodationName);

                databaseReference.updateChildren(accommodationMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Update Accommodation: Success");
                        }
                        else{
                            Log.d(TAG, "Update Accommodation: Failed!!!");
                        }
                    }
                });

                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonCancleOnclick(){
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                startActivity(intent);
            }
        });
    }



}
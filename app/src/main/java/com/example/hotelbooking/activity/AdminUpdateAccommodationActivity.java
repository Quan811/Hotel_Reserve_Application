package com.example.hotelbooking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.ImageURLAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.List;

public class AdminUpdateAccommodationActivity extends AppCompatActivity {
    TextInputEditText edtAccommodationName, edtLocation, edtDescription, edtAccommodationType, edtThumbnail;
    MaterialButton buttonSave, buttonCancle;
    RecyclerView rcvURL, rcvRoom;
    ImageURLAdapter urlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_accommodation);

        initView();
        setInitData();
        setRcvURL();
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
            edtDescription.setText(accommodation.getAccommodationDescription());
            edtAccommodationType.setText(accommodation.getAccommodationType());
            edtThumbnail.setText(accommodation.getThumbnail());
        }

    }
    private void initView() {
        edtAccommodationName = findViewById(R.id.edt_accommodation_name);
        edtLocation = findViewById(R.id.edt_location);
        edtDescription = findViewById(R.id.edt_accommodation_des);
        edtAccommodationType = findViewById(R.id.edt_accommodation_type);
        edtThumbnail = findViewById(R.id.edt_thumbnail);
        buttonSave = findViewById(R.id.button_save);
        buttonCancle = findViewById(R.id.button_cancle);
        rcvURL = findViewById(R.id.rcv_url);
        rcvRoom = findViewById(R.id.rcv_room);
    }

    private void setRcvURL(){
        Accommodation accommodation = getAccommodation();
        List<String> listURL = accommodation.getImgURL();
        urlAdapter = new ImageURLAdapter(getApplicationContext(), listURL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvURL.setLayoutManager(layoutManager);
        rcvURL.setAdapter(urlAdapter);
    }
    private void setRcvRoom(){
        
    }
}
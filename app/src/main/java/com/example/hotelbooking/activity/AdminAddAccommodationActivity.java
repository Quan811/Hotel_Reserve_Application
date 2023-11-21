package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class AdminAddAccommodationActivity extends AppCompatActivity {
    TextInputEditText edtAccommodationName, edtLocation, edtRating,edtThumbnail,
            edtAccommodationDes, edtAccommodationType, edtAddImage,edtRoomType,
            edtPrice, edtRoomDes, edtNumberAvailable, edtRoomImage;
    MaterialButton buttonAddImage, buttonAddRoom, buttonAddAccommodation, buttonCancle;
    RecyclerView rcvImageURL, rcvRoom;
    DatabaseReference databaseReference;
    List<String> listURL = new ArrayList<>();
    List<Room> listRoom = new ArrayList<>();
    ImageURLAdapter urlAdapter;
    AdminRoomAdapter adminRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_accommodation);

        initView();
        setRcvURL();
        setRcvRoom();
        addNewImage();
        addRoom();
        buttonAddAccommodationOnClick();
        buttonCancleOnClick();
    }

    private void initView() {
        edtAccommodationName = findViewById(R.id.edt_accommodation_name);
        edtAccommodationDes = findViewById(R.id.edt_accommodation_des);
        edtLocation = findViewById(R.id.edt_location);
        edtRating = findViewById(R.id.edt_rating);
        edtThumbnail = findViewById(R.id.edt_thumbnail);
        edtAccommodationType = findViewById(R.id.edt_accommodation_type);
        edtAddImage = findViewById(R.id.edt_add_image);
        edtRoomType = findViewById(R.id.edt_room_type);
        edtPrice = findViewById(R.id.edt_price);
        edtRoomDes = findViewById(R.id.edt_room_des);
        edtNumberAvailable = findViewById(R.id.edt_number_available);
        edtRoomImage = findViewById(R.id.edt_room_img);
        buttonAddImage = findViewById(R.id.button_add_image);
        buttonAddRoom = findViewById(R.id.button_add_room);
        buttonAddAccommodation = findViewById(R.id.button_add_accommodation);
        buttonCancle = findViewById(R.id.button_cancle);
        rcvImageURL = findViewById(R.id.rcv_url);
        rcvRoom = findViewById(R.id.rcv_room);

    }

    private void setRcvURL(){
        urlAdapter = new ImageURLAdapter(getApplicationContext(), listURL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvImageURL.setLayoutManager(layoutManager);
        rcvImageURL.setAdapter(urlAdapter);
    }
    private void setRcvRoom(){
        adminRoomAdapter = new AdminRoomAdapter(getApplicationContext(), listRoom);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvRoom.setLayoutManager(layoutManager);
        rcvRoom.setAdapter(adminRoomAdapter);
    }

    private void buttonAddAccommodationOnClick(){
        buttonAddAccommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accName = edtAccommodationName.getText().toString().trim();
                String location = edtLocation.getText().toString().trim();
                String accDes = edtAccommodationDes.getText().toString().trim();
                String rating = edtRating.getText().toString().trim();
                String thumbnail = edtThumbnail.getText().toString().trim();
                String accType = edtAccommodationType.getText().toString().trim();

                Accommodation accommodation = new Accommodation(
                        accName, accDes, rating, accType, thumbnail, listURL, location, listRoom);

                databaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference("accommodations")
                            .child(accName);
                databaseReference.setValue(accommodation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "Add New Accommodation: Success ");
                                    finish();
                                    FancyToast.makeText(getApplicationContext(),
                                            "Add Succesful",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.SUCCESS,
                                            false);
                                }else {
                                    Log.d(TAG, "Add New Accommodation: Failed ");
                                    FancyToast.makeText(getApplicationContext(),
                                            "Add Failed!!!",
                                            FancyToast.LENGTH_SHORT,
                                            FancyToast.ERROR,
                                            false);
                                    finish();
                                }
                            }
                        });
                Log.d(TAG, "listURL size: "+listURL.size());
                Log.d(TAG, "lisRoom size: "+listRoom.size());

            }
        });
    }
    private void addNewImage(){
        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listURL.add(edtAddImage.getText().toString().trim());
                if (urlAdapter == null) {
                    urlAdapter = new ImageURLAdapter(getApplicationContext(), listURL);
                    rcvImageURL.setAdapter(urlAdapter);
                } else {
                    // Đã được khởi tạo, cập nhật dữ liệu
                    urlAdapter.notifyDataSetChanged();
                }
                edtAddImage.setText("");
            }
        });
    }

    private void addRoom(){
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomType = edtRoomType.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                String roomDes = edtRoomDes.getText().toString().trim();
                String numberAvailabe = edtNumberAvailable.getText().toString().trim();
                String roomImage = edtRoomImage.getText().toString().trim();

                Room room = new Room(roomType, price, roomDes, numberAvailabe, roomImage);
                listRoom.add(room);

                if (adminRoomAdapter == null) {
                    adminRoomAdapter = new AdminRoomAdapter(getApplicationContext(), listRoom);
                    rcvRoom.setAdapter(adminRoomAdapter);
                } else {
                    // Đã được khởi tạo, cập nhật dữ liệu
                    adminRoomAdapter.notifyDataSetChanged();
                }

                edtRoomType.setText("");
                edtPrice.setText("");
                edtRoomDes.setText("");
                edtNumberAvailable.setText("");
                edtRoomImage.setText("");


            }
        });
    }

    private void buttonCancleOnClick(){
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




}
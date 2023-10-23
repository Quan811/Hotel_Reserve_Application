package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.RoomAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Room;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.List;

public class ReverseRoomActivity extends AppCompatActivity {
    RecyclerView rcvRoom;
    ImageView buttonBack;
    RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reverse_room);

        initView();

        //set data for RecyclerView
        roomAdapter = new RoomAdapter(getApplicationContext(), getListRoom());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        rcvRoom.setLayoutManager(layoutManager);
        rcvRoom.setAdapter(roomAdapter);
    }

    private List<Room> getListRoom() {
        Accommodation accommodation = getAccommodation();
        if(accommodation == null){
            Log.d(TAG, "Khong co accommodation");
        }
        else {
            Log.d(TAG, "Co accommodation");

        }
        List<Room> list = accommodation.getRoomList();
        if(list == null){
            Log.d(TAG, "Khong co du lieu phong");
        }
        else {
            Log.d(TAG, "Co du lieu phong");
        }
        return list;
    }

    private Accommodation getAccommodation(){
        Accommodation accommodation = new Accommodation();
        Intent intent = getIntent();
        if(intent != null){
            Serializable serializable = intent.getSerializableExtra("accommodation");
            if(serializable != null){
                accommodation = (Accommodation) serializable;
            }
        }
        return accommodation;
    }
    private void initView() {
        rcvRoom = findViewById(R.id.rcv_room);
        buttonBack = findViewById(R.id.button_back);
    }
}
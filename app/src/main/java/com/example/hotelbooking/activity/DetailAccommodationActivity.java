package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.DetailAccommodationAdapter;
import com.example.hotelbooking.adapter.RoomAdapter;
import com.example.hotelbooking.data.AccommodationHolder;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailAccommodationActivity extends AppCompatActivity {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    DetailAccommodationAdapter detailAccommodationAdapter;
    ImageView buttonBack;
    RecyclerView rcvRoom;
    TextView accommodationName, accommodationRating, accommodationLocation, accommodationDes, accommodationType;
    Runnable autoScrollRunnable;
    final Handler autoScrollHandler = new Handler();
    RoomAdapter roomAdapter;
    AccommodationHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_accommodation);

        //anh xa view
        initView();

        //set ViewPager
        setViewPager();

        //set du lieu
        setData();

        //click button back
        setButtonBackClick();

        //room RecycleView
        setRoomRcv();

        //gui du lieu di
        sendAccommodation();



    }

    private void sendAccommodation(){
        Accommodation accommodation = getAccommodation();
        holder.getInstance().setDataToPass(accommodation);
        if(accommodation == null){
            Log.d(TAG, "sendAccommodation: du lieu gui di trong");
        }else {
            Log.d(TAG, "sendAccommodation: co du lieu gui di");
            Log.d(TAG, "acc name: "+ accommodation.getAccommodationName());
            Log.d(TAG, "location: "+ accommodation.getLocation());
        }
    }
    private void setButtonBackClick(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private List<Room> getListRoom() {
        Accommodation accommodation = getAccommodation();
        List<Room> list = accommodation.getRoomList();
        return list;
    }

    private void setData(){
        Accommodation accommodation = getAccommodation();
        if(accommodation != null){
            accommodationName.setText(accommodation.getAccommodationName());
            accommodationDes.setText(accommodation.getAccommodationDescription());
            accommodationLocation.setText(accommodation.getLocation());
            accommodationRating.setText(accommodation.getAccommodationRating());
            accommodationType.setText(accommodation.getAccommodationType());
        }
        else{
            Log.d(TAG, "Ko co du lieu kia");
        }
    }

    private void setViewPager(){
        //set anh cho ViewPager
        detailAccommodationAdapter = new DetailAccommodationAdapter(getApplicationContext(), getListImage());
        viewPager.setAdapter(detailAccommodationAdapter);

        //Circle indicator
        circleIndicator.setViewPager(viewPager);
        detailAccommodationAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        startAutoScroll();
    }

    private void setRoomRcv(){
        roomAdapter = new RoomAdapter(getApplicationContext(), getListRoom());
        Accommodation accommodation = getAccommodation();
        roomAdapter.setAccommodation(accommodation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);
        rcvRoom.setLayoutManager(layoutManager);
        rcvRoom.setAdapter(roomAdapter);
    }

    private Accommodation getAccommodation(){
        Accommodation accommodation = new Accommodation();
        Intent intent = getIntent();
        if(intent != null){
            Serializable serializable = intent.getSerializableExtra("accommodation_from_location_to_detail_accommodation");
            if(serializable != null){
                accommodation = (Accommodation) serializable;
            }
        }
        return accommodation;
    }

    private List<String> getListImage() {
        List<String> list = new ArrayList<>();
        Accommodation accommodation = getAccommodation();
        list = accommodation.getImgURL();
        return list;
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager_acc_detail);
        circleIndicator = findViewById(R.id.circle_indicator);
        accommodationName = findViewById(R.id.accommodation_name);
        accommodationRating = findViewById(R.id.accommodation_rating);
        accommodationLocation = findViewById(R.id.location);
        accommodationDes = findViewById(R.id.accommodation_description);
        buttonBack = findViewById(R.id.button_back);
        accommodationType = findViewById(R.id.accommodation_type);
        rcvRoom = findViewById(R.id.rcv_room);

    }

    private void startAutoScroll(){
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItem = viewPager.getAdapter().getCount();

                if(currentItem < totalItem-1){
                    viewPager.setCurrentItem(currentItem + 1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }

                autoScrollHandler.postDelayed(autoScrollRunnable, 3000);
            }
        };
        autoScrollHandler.postDelayed(autoScrollRunnable, 3000);
    }


}
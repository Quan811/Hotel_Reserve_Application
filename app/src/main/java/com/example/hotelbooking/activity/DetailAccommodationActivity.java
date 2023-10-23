package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.hotelbooking.model.Accommodation;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailAccommodationActivity extends AppCompatActivity {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    DetailAccommodationAdapter detailAccommodationAdapter;
    ImageView buttonBack;
    MaterialButton  buttonReserve;

    TextView accommodationName, accommodationRating, accommodationLocation, accommodationDes, accommodationType;
    Runnable autoScrollRunnable;
    final Handler autoScrollHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_accommodation);

        initView();

        //set anh cho ViewPager
        detailAccommodationAdapter = new DetailAccommodationAdapter(getApplicationContext(), getListImage());
        viewPager.setAdapter(detailAccommodationAdapter);

        //Circle indicator
        circleIndicator.setViewPager(viewPager);
        detailAccommodationAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        startAutoScroll();

        //set du lieu
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

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), DetailAccommodationActivity.class);
                startActivity(intent);
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReverseRoomActivity.class);
                intent.putExtra("accommodation", (Serializable) getAccommodation());
                startActivity(intent);
            }
        });

    }

    private Accommodation getAccommodation(){
        Accommodation accommodation = new Accommodation();
        Intent intent = getIntent();
        if(intent != null){
            Serializable serializable = intent.getSerializableExtra("object");
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
        accommodationLocation = findViewById(R.id.accommodation_location);
        accommodationDes = findViewById(R.id.accommodation_description);
        buttonBack = findViewById(R.id.button_back);
        buttonReserve = findViewById(R.id.button_reserve);
        accommodationType = findViewById(R.id.accommodation_type);


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
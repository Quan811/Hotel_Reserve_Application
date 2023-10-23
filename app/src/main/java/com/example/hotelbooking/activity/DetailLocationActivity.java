package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.DetailLocationViewPagerAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.viewmodel.AccommodationViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class DetailLocationActivity extends AppCompatActivity {
    TextView tvLocation;
    ImageView buttonBack;
    RecyclerView rcvLocationDetail;
    DetailLocationViewPagerAdapter detailLocationViewPagerAdapter;
    AccommodationViewModel accommodationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_location);
        initView();

        tvLocation.setText(getQueryText());
        //init ViewModel
        accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);
        accommodationViewModel.getAllAccommodations(new AccommodationViewModel.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Accommodation> accommodations) {
                List<Accommodation> listDataReturn = new ArrayList<>();
                String query = getQueryText().toLowerCase();
                for(Accommodation accommodation : accommodations){
                    if(query.equals(accommodation.getLocation().toLowerCase())){
                        listDataReturn.add(accommodation);
                    }
                }

                if(listDataReturn.size() != 0){
                    setDataRecyclerView(listDataReturn);
                }
                else{
                    FancyToast.makeText(getApplicationContext(),
                                    "No result match !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.INFO,
                                    false)
                            .show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });

        //Button Back Click
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ClientHomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setDataRecyclerView(List<Accommodation> list){
        detailLocationViewPagerAdapter = new DetailLocationViewPagerAdapter(getApplicationContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvLocationDetail.setLayoutManager(linearLayoutManager);
        rcvLocationDetail.setAdapter(detailLocationViewPagerAdapter);
    }


    private String getQueryText(){
        Intent intent = getIntent();
        String queryText = "";
        if(intent != null){
            queryText = intent.getStringExtra("search_query");
        }
        return queryText;
    }



    private void initView() {
        tvLocation = findViewById(R.id.tv_location);
        buttonBack = findViewById(R.id.button_back);
        rcvLocationDetail = findViewById(R.id.rcv_location_detail);
    }



}
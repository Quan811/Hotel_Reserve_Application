package com.example.hotelbooking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.ClientAccommodationsAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.viewmodel.AllAccommodationViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientAccommodationsActivity extends AppCompatActivity {
    TextView tvLocation;
    ImageView buttonBack;
    RecyclerView rcvAccommodations;
    ClientAccommodationsAdapter clientAccommodationsAdapter;
    AllAccommodationViewModel allAccommodationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_location);
        initView();

        tvLocation.setText(getQueryText());

        //init ViewModel
        showRecycleView();

        //Button Back Click
        onButtonBackClick();


    }

    private void showRecycleView(){
        allAccommodationViewModel = new ViewModelProvider(this).get(AllAccommodationViewModel.class);
        allAccommodationViewModel.getAccommodationsLiveData().observe(this, new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> accommodations) {
                List<Accommodation> listDataReturn = new ArrayList<>();
                String query = getQueryText().toLowerCase();
                for (Accommodation accommodation : accommodations) {
                    String location = formatData(accommodation.getLocation());
                    if (query.equals(location)) {
                        listDataReturn.add(accommodation);
                    }
                }

                if (listDataReturn.size() != 0) {
                    setDataRecyclerView(listDataReturn);
                } else {
                    FancyToast.makeText(getApplicationContext(),
                                    "No result match!",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.INFO,
                                    false)
                            .show();
                }
            }
        });
    }
    private void onButtonBackClick(){
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
        clientAccommodationsAdapter = new ClientAccommodationsAdapter(getApplicationContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rcvAccommodations.setLayoutManager(linearLayoutManager);
        rcvAccommodations.setAdapter(clientAccommodationsAdapter);
    }


    private String getQueryText(){
        Intent intent = getIntent();
        String queryText = "";
        if(intent != null){
            queryText = intent.getStringExtra("search_query");
        }
        return queryText;
    }

    private String formatData(String s){
        String x = "";
        x = vietnameseToEnglish(s);
        String[] split = x.split(",");
        String result = split[0].trim();
        return result;
    }


    public static String vietnameseToEnglish(String str) {
        str = str.toLowerCase();
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutDiacritics = pattern.matcher(nfdNormalizedString).replaceAll("");
        withoutDiacritics = withoutDiacritics.replace("đ", "d");

        return withoutDiacritics;
    }


    private void initView() {
        tvLocation = findViewById(R.id.tv_location);
        buttonBack = findViewById(R.id.button_back);
        rcvAccommodations = findViewById(R.id.rcv_location_detail);
    }



}
package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.AdminAccommodationsAdapter;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.viewmodel.AllAccommodationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminAccommodationsActivity extends AppCompatActivity {
    RecyclerView rcvAccommodations;
    AdminAccommodationsAdapter adminAccommodationsAdapter;
    AllAccommodationViewModel allAccommodationViewModel;
    SearchView searchView;
    ImageView buttonBack;
    FloatingActionButton buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accommodations);

        initView();
        setRcvAccommodations();
        searchViewOnClick();
        buttonBackOnClick();
    }

    private void setRcvAccommodations(){
        allAccommodationViewModel = new ViewModelProvider(this).get(AllAccommodationViewModel.class);
        allAccommodationViewModel.getAccommodationsLiveData().observe(this, new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> accommodations) {
                setDataRcvAccommodations(accommodations);
            }
        });
    }
    private void setDataRcvAccommodations(List<Accommodation> list){
        if(list != null){
            adminAccommodationsAdapter = new AdminAccommodationsAdapter(getApplicationContext(), list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_left_to_right);
            rcvAccommodations.setLayoutAnimation(animation);
            adminAccommodationsAdapter.notifyDataSetChanged();
            rcvAccommodations.setLayoutManager(linearLayoutManager);
            rcvAccommodations.setAdapter(adminAccommodationsAdapter);
        }
        else{
            Log.d(TAG, "setDataRcvAccommodations: ko co du lieu");
        }
    }


    private void initView() {
        rcvAccommodations = findViewById(R.id.rcv_accommodations);
        searchView = findViewById(R.id.searchview);
        buttonBack = findViewById(R.id.button_back);
        buttonAdd = findViewById(R.id.button_add);
    }

    private void buttonBackOnClick(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void searchViewOnClick(){
        allAccommodationViewModel = new ViewModelProvider(this).get(AllAccommodationViewModel.class);
        allAccommodationViewModel.getAccommodationsLiveData().observe(this, new Observer<List<Accommodation>>() {
            @Override
            public void onChanged(List<Accommodation> accommodations) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        List<Accommodation> listDataReturn = new ArrayList<>();
                        String query1 = query.toLowerCase();
                        for (Accommodation accommodation : accommodations) {
                            String location = formatData(accommodation.getLocation());
                            if (query1.equals(location)) {
                                listDataReturn.add(accommodation);
                            }
                        }

                        if (listDataReturn.size() != 0) {
                            setDataRcvAccommodations(listDataReturn);
                        } else {
                            FancyToast.makeText(getApplicationContext(),
                                            "No result match!",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.INFO,
                                            false)
                                    .show();
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return true;
                    }
                });
            }
        });

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
}
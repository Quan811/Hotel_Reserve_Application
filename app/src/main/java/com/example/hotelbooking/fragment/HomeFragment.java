package com.example.hotelbooking.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.ClientAccommodationsActivity;
import com.example.hotelbooking.adapter.HomeCategoryAdapter;
import com.example.hotelbooking.adapter.HomePopularDesAdapter;
import com.example.hotelbooking.adapter.HomePropertyTypeAdapter;
import com.example.hotelbooking.adapter.HomeSlideImageAdapter;
import com.example.hotelbooking.model.Category;
import com.example.hotelbooking.model.Client;
import com.example.hotelbooking.model.Photo;
import com.example.hotelbooking.model.PopularDestination;
import com.example.hotelbooking.model.PropertyType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    TextView clientName;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    HomeSlideImageAdapter homeSlideImageAdapter;
    final Handler autoScrollHandler = new Handler();
    Runnable autoScrollRunnable;
    RecyclerView rcvCategory, rcvPopularDestination, rcvPropertyType;
    HomeCategoryAdapter categoryAdapter;
    HomePopularDesAdapter popularDesAdapter;
    HomePropertyTypeAdapter propertyTypeAdapter;
    SearchView searchView;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager_homeview);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvPopularDestination = view.findViewById(R.id.rcv_popular_destination);
        rcvPropertyType = view.findViewById(R.id.rcv_property_type);
        searchView = view.findViewById(R.id.searchview);
        clientName = view.findViewById(R.id.client_name);

        //set Client Name
        setClientName();
        //set data for Viewpager
        setViewPager();

        //Set data for RecycleView Category
        setRcvCategory();

        //Set data for RecycleView Popular Destination
        setRcvPopularDestination();

        //Set data for RecycleView Property Type
        setRcvPropertyType();

        //Click on SearchView
        onSearchViewClicked();


        return view;
    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.nhatrang));
        list.add(new Photo(R.drawable.caobang));
        list.add(new Photo(R.drawable.danang));
        list.add(new Photo(R.drawable.halong));
        list.add(new Photo(R.drawable.vungtau));
        list.add(new Photo(R.drawable.phuquoc));
        list.add(new Photo(R.drawable.hoian));

        return list;
    }

    private List<Category> getListCategory(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.ic_cruise, "Cruise"));
        list.add(new Category(R.drawable.ic_car_rental, "Car Rental"));
        list.add(new Category(R.drawable.ic_villa_and_apartment, "Villa & Apartment"));
        list.add(new Category(R.drawable.ic_flight, "Flights"));
        list.add(new Category(R.drawable.ic_hotel, "Hotel"));
        list.add(new Category(R.drawable.ic_bus, "Bus & Shuttle"));
        list.add(new Category(R.drawable.ic_voucher, "Voucher"));
        list.add(new Category(R.drawable.ic_point, "Point"));
        list.add(new Category(R.drawable.ic_insurance, "Insurance"));
        list.add(new Category(R.drawable.ic_business_partner, "Bussiness Partner"));
        list.add(new Category(R.drawable.ic_weather, "Weather"));
        return list;
    }

    private List<PopularDestination> getListPopularDes() {
        List<PopularDestination> list = new ArrayList<>();
        list.add(new PopularDestination(R.drawable.hanoi_small_img, "Hà Nội", 3256));
        list.add(new PopularDestination(R.drawable.danang_small_img, "Đà Nẵng", 2543));
        list.add(new PopularDestination(R.drawable.nhatrang_smail_img, "Nha Trang", 2646));
        list.add(new PopularDestination(R.drawable.saigon_small_img, "Sài Gòn", 5283));
        list.add(new PopularDestination(R.drawable.sapa_small_img, "Sapa", 278));
        list.add(new PopularDestination(R.drawable.dalat_small_img, "Đà Lạt", 1834));
        list.add(new PopularDestination(R.drawable.halong_small_img, "Hạ Long", 2837));
        list.add(new PopularDestination(R.drawable.hue_small_img, "Huế", 1723));
        list.add(new PopularDestination(R.drawable.hoian_small_img, "Hội An", 987));
        list.add(new PopularDestination(R.drawable.langco_small_img, "Lăng Cô", 572));
        list.add(new PopularDestination(R.drawable.phanthiet_small_mg, "Phan Thiết", 739));
        list.add(new PopularDestination(R.drawable.samson_small_img, "Sầm Sơn", 483));
        list.add(new PopularDestination(R.drawable.vungtau_small_img, "Vũng Tàu", 857));

        return list;
    }

    private List<PropertyType> getListPropertyType() {
        List<PropertyType> list = new ArrayList<>();
        list.add(new PropertyType(R.drawable.resorts, "Resorts"));
        list.add(new PropertyType(R.drawable.villas, "Villas"));
        list.add(new PropertyType(R.drawable.hotels, "Hotels"));
        list.add(new PropertyType(R.drawable.apartments, "Apartments"));
        list.add(new PropertyType(R.drawable.cabins, "Cabins"));
        list.add(new PropertyType(R.drawable.cottages, "Cottages"));
        list.add(new PropertyType(R.drawable.glamping, "Glampings"));
        list.add(new PropertyType(R.drawable.motel, "Motels"));
        list.add(new PropertyType(R.drawable.services_apartments, "Service Apartments"));
        list.add(new PropertyType(R.drawable.vacation_homes, "Vacation Homes"));

        return list;
    }

    private void startAutoScroll() {
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter().getCount();

                if (currentItem < totalItems - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }

                autoScrollHandler.postDelayed(autoScrollRunnable, 3000);
            }
        };

        autoScrollHandler.postDelayed(autoScrollRunnable, 3000);
    }

    private void setViewPager(){
        homeSlideImageAdapter = new HomeSlideImageAdapter(getContext(), getListPhoto());
        viewPager.setAdapter(homeSlideImageAdapter);
        circleIndicator.setViewPager(viewPager);
        homeSlideImageAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        startAutoScroll();
    }


    private void setRcvCategory(){
        categoryAdapter = new HomeCategoryAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,
                GridLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }
    private void setRcvPopularDestination(){
        popularDesAdapter = new HomePopularDesAdapter(getContext());
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),1,
                GridLayoutManager.HORIZONTAL, false);
        rcvPopularDestination.setLayoutManager(gridLayoutManager1);
        popularDesAdapter.setData(getListPopularDes());
        rcvPopularDestination.setAdapter(popularDesAdapter);
    }
    private void setRcvPropertyType(){
        propertyTypeAdapter = new HomePropertyTypeAdapter(getContext());
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(),1,
                GridLayoutManager.HORIZONTAL, false);
        rcvPropertyType.setLayoutManager(gridLayoutManager2);
        propertyTypeAdapter.setData(getListPropertyType());
        rcvPropertyType.setAdapter(propertyTypeAdapter);
    }


    private void onSearchViewClicked(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), ClientAccommodationsActivity.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setClientName(){
        databaseReference = FirebaseDatabase.getInstance().getReference("clients");
        String userID = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "userID: "+ userID);
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Client client = snapshot.getValue(Client.class);
                    if (client != null) {
                        Log.d(TAG, "onDataChange: client not null");
                        clientName.setText(client.getFullName());
                        Log.d(TAG, "onDataChange: "+client.getFullName());
                    } else {
                        Log.d(TAG, "onDataChange: client null");
                    }
                } else {
                    Log.d(TAG, "onDataChange: snapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
    }
}

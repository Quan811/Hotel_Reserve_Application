package com.example.hotelbooking.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.ClientHomeActivity;
import com.example.hotelbooking.adapter.MyReserveAdapter;
import com.example.hotelbooking.model.Order;
import com.example.hotelbooking.viewmodel.ClientOrdersViewModel;

import java.util.List;

public class MyReserveFragment extends Fragment {
    RecyclerView rcvMyReserve;
    ImageView buttonBack;
    MyReserveAdapter adapter;
    ClientOrdersViewModel clientOrdersViewModel;
    LinearLayout noReservation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reserve, container, false);

        noReservation = view.findViewById(R.id.no_reservation_yet);
        rcvMyReserve = view.findViewById(R.id.rcv_myreserve);
        buttonBack = view.findViewById(R.id.button_back);

        setRcvMyReserve();
        onButtonBackClicked();

        return view;
    }

    private void onButtonBackClicked(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientHomeActivity.class);
                startActivity(intent);
            }
        });
    }
     private void setRcvMyReserve(){

        clientOrdersViewModel = new ViewModelProvider(this).get(ClientOrdersViewModel.class);
        clientOrdersViewModel.getOrdersLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> listOrder) {
                if (listOrder.size() != 0) {
                    noReservation.setVisibility(View.GONE);
                    rcvMyReserve.setVisibility(View.VISIBLE);
                    Log.d(TAG, "List Order: " + listOrder.size());
                    adapter = new MyReserveAdapter(getContext(), listOrder);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rcvMyReserve.setLayoutManager(manager);
                    rcvMyReserve.setAdapter(adapter);
                }else{
                    noReservation.setVisibility(View.VISIBLE);
                }

            }
        });
     }




}

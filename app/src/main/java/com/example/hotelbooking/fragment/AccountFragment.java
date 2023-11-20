package com.example.hotelbooking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Client;
import com.example.hotelbooking.viewmodel.ClientInfoViewModel;

public class AccountFragment extends Fragment {
    ClientInfoViewModel clientInfoViewModel;
    TextView clientName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        clientName = view.findViewById(R.id.client_name);

        setClientInfo();
        return view;
    }

    private void setClientInfo(){

        clientInfoViewModel = new ViewModelProvider(this).get(ClientInfoViewModel.class);
        clientInfoViewModel.getClientLiveData().observe(getViewLifecycleOwner(), new Observer<Client>() {
            @Override
            public void onChanged(Client client) {
                if(client != null){
                    clientName.setText(client.getFullName());
                }
            }
        });
    }
}

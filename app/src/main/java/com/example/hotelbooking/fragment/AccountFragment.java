package com.example.hotelbooking.fragment;

import android.content.Intent;
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
import com.example.hotelbooking.activity.LoginActivity;
import com.example.hotelbooking.model.Client;
import com.example.hotelbooking.viewmodel.ClientInfoViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    ClientInfoViewModel clientInfoViewModel;
    TextView clientName;
    MaterialButton buttonLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        clientName = view.findViewById(R.id.client_name);
        buttonLogout = view.findViewById(R.id.button_logout);

        setClientInfo();
        buttonLogoutOnClick();
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

    private void buttonLogoutOnClick(){
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

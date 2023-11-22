package com.example.hotelbooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class AccountFragment extends Fragment {
    ClientInfoViewModel clientInfoViewModel;
    TextView clientName;
    MaterialButton buttonLogout, buttonChangeInfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        clientName = view.findViewById(R.id.client_name);
        buttonLogout = view.findViewById(R.id.button_logout);
        buttonChangeInfo = view.findViewById(R.id.button_change_info);

        setClientInfo();
        buttonLogoutOnClick();
        buttonChangeInfoOnClick();
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

    private void buttonChangeInfoOnClick(){
        buttonChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.client_change_info))
                        .setGravity(Gravity.BOTTOM) // Đặt vị trí ở dưới cùng
                        .setExpanded(true, 900)
                        .create();


                View view = dialogPlus.getHolderView();
                TextInputEditText edtFullName = view.findViewById(R.id.edt_fullname);
                TextInputEditText edtPhoneNumber = view.findViewById(R.id.edt_phonenumber);
                MaterialButton buttonSave = view.findViewById(R.id.button_save);
                MaterialButton buttonCancle = view.findViewById(R.id.button_cancle);


                clientInfoViewModel = new ViewModelProvider(requireActivity()).get(ClientInfoViewModel.class);
                clientInfoViewModel.getClientLiveData().observe(requireActivity(), new Observer<Client>() {
                    @Override
                    public void onChanged(Client client) {
                        dialogPlus.show();
                        edtFullName.setText(client.getFullName());
                        edtPhoneNumber.setText(client.getPhoneNumber());
                        buttonSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String fullName = edtFullName.getText().toString();
                                String phoneNumber = edtPhoneNumber.getText().toString();
                                String clientID = client.getClientID();

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("clients");
                                databaseReference.child(clientID).child("fullName").setValue(fullName);
                                databaseReference.child(clientID).child("phoneNumber").setValue(phoneNumber);
                                dialogPlus.dismiss();
                            }
                        });

                        buttonCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogPlus.dismiss();
                            }
                        });

                    }
                });
            }
        });
    }
}

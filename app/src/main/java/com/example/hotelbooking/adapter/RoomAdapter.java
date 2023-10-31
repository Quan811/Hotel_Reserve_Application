package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.ReserveRoomActivity;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Room;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    Context context;
    List<Room> listRoom;
    Accommodation accommodation;


    public void setAccommodation(Accommodation accommodation){
        this.accommodation = accommodation;
    }

    public RoomAdapter(Context context, List<Room> listRoom) {
        this.context = context;
        this.listRoom = listRoom;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = listRoom.get(position);
        if(room == null){
            return;
        }
        holder.roomType.setText(room.getRoomType());
        holder.roomDes.setText(room.getRoomDescription());

        String roomPrice = room.getPrice();
        StringBuilder stringBuilder = new StringBuilder(roomPrice).reverse();
        for(int i=3; i<stringBuilder.length(); i+=4){
            stringBuilder.insert(i, ".");
        }
        roomPrice = stringBuilder.reverse().toString();
        holder.price.setText(roomPrice);
        holder.numberAvailable.setText(room.getNumberAvailable());
        Glide.with(holder.roomImg.getContext())
                .load(room.getRoomImg())
                .centerCrop()
                .into(holder.roomImg);


        holder.buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReserveRoomActivity.class);
                Intent intent1 = new Intent(v.getContext(), ReserveRoomActivity.class);
                intent.putExtra("room_from_accommodation", room );
                intent1.putExtra("accommodation_from_accommodation", accommodation );
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listRoom != null){
            return listRoom.size();
        }
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImg;
        TextView roomType, numberAvailable, price, roomDes;
        MaterialButton buttonChoose;
        public RoomViewHolder(@NonNull View view) {
            super(view);
            roomType = view.findViewById(R.id.accommodation_name);
            numberAvailable = view.findViewById(R.id.number_available);
            price = view.findViewById(R.id.price);
            roomDes = view.findViewById(R.id.room_des);
            roomImg = view.findViewById(R.id.room_img);
            buttonChoose = view.findViewById(R.id.button_choose);
        }
    }


}

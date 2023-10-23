package com.example.hotelbooking.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    Context context;
    List<Room> listRoom;

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
        holder.price.setText(room.getPrice());
        holder.numberAvailable.setText(room.getNumberAvailable());
        Glide.with(holder.roomImg.getContext())
                .load(room.getRoomImg())
                .centerCrop()
                .into(holder.roomImg);
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
        public RoomViewHolder(@NonNull View view) {
            super(view);
            roomType = view.findViewById(R.id.room_type);
            numberAvailable = view.findViewById(R.id.number_available);
            price = view.findViewById(R.id.price);
            roomDes = view.findViewById(R.id.room_des);
            roomImg = view.findViewById(R.id.room_img);
        }
    }
}

package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.Gravity;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.AdminRoomViewHolder> {
    Context context;
    List<Room> listRoom;

    public AdminRoomAdapter(Context context, List<Room> list) {
        this.context = context;
        this.listRoom = list;
    }


    @NonNull
    @Override
    public AdminRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_room, parent, false);
        return new AdminRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomViewHolder holder, int position) {
        Room room = listRoom.get(position);
        if (room == null) {
            return;
        }
        holder.roomType.setText(room.getRoomType());
        holder.roomDes.setText(room.getRoomDescription());

        String roomPrice = room.getPrice();
        StringBuilder stringBuilder = new StringBuilder(roomPrice).reverse();
        for (int i = 3; i < stringBuilder.length(); i += 4) {
            stringBuilder.insert(i, ".");
        }
        roomPrice = stringBuilder.reverse().toString();
        holder.price.setText(roomPrice);
        holder.numberAvailable.setText(room.getNumberAvailable());
        Glide.with(holder.roomImg.getContext())
                .load(room.getRoomImg())
                .centerCrop()
                .into(holder.roomImg);

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_room))
                        .setExpanded(true, 1500)
                        .create();

                View view = dialogPlus.getHolderView();
                TextInputEditText roomTypeDialog = view.findViewById(R.id.edt_room_type);
                TextInputEditText priceDialog = view.findViewById(R.id.edt_price);
                TextInputEditText roomDesDialog = view.findViewById(R.id.edt_room_des);
                TextInputEditText numberAvalableDialog = view.findViewById(R.id.edt_number_available);
                TextInputEditText roomImgURLDialog = view.findViewById(R.id.edt_room_img);
                MaterialButton buttonSaveDialog = view.findViewById(R.id.button_save);
                MaterialButton buttonCancleDialog = view.findViewById(R.id.button_cancle);

                // set text for edittext
                roomTypeDialog.setText(room.getRoomType());
                priceDialog.setText(room.getPrice());
                roomDesDialog.setText(room.getRoomDescription());
                numberAvalableDialog.setText(room.getNumberAvailable());
                roomImgURLDialog.setText(room.getRoomImg());

                dialogPlus.show();

                buttonSaveDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomType = roomTypeDialog.getText().toString();
                        String roomDes = roomDesDialog.getText().toString();
                        String roomPrice = removeDots(priceDialog.getText().toString());
                        String numberAvailable = numberAvalableDialog.getText().toString();
                        String roomImg = roomImgURLDialog.getText().toString();

                        holder.roomType.setText(roomType);
                        holder.roomDes.setText(roomDes);

                        StringBuilder stringBuilder = new StringBuilder(roomPrice).reverse();
                        for (int i = 3; i < stringBuilder.length(); i += 4) {
                            stringBuilder.insert(i, ".");
                        }
                        roomPrice = stringBuilder.reverse().toString();
                        holder.price.setText(roomPrice);
                        holder.numberAvailable.setText(numberAvailable);
                        Glide.with(holder.roomImg.getContext())
                                .load(roomImg)
                                .centerCrop()
                                .into(holder.roomImg);

                        Room updatedRoom = new Room(roomType, roomPrice, roomDes, numberAvailable, roomImg);
                        updateRoom(holder.getAdapterPosition(), updatedRoom);

                        dialogPlus.dismiss();
                    }
                });

                buttonCancleDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }

    public List<Room> getListRoom() {
        return listRoom;
    }

    public void updateRoom(int position, Room updatedRoom) {
        listRoom.set(position, updatedRoom);
        notifyItemChanged(position);
    }

    public class AdminRoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImg;
        TextView roomType, numberAvailable, price, roomDes;
        MaterialButton buttonEdit;

        public AdminRoomViewHolder(@NonNull View view) {
            super(view);
            roomType = view.findViewById(R.id.room_type);
            numberAvailable = view.findViewById(R.id.number_available);
            price = view.findViewById(R.id.price);
            roomDes = view.findViewById(R.id.room_des);
            roomImg = view.findViewById(R.id.room_img);
            buttonEdit = view.findViewById(R.id.button_edit);
        }
    }
    private static String removeDots(String input) {
        // Sử dụng StringBuilder để xử lý chuỗi một cách hiệu quả
        StringBuilder stringBuilder = new StringBuilder(input);

        // Xóa các dấu chấm (.)
        int dotIndex;
        while ((dotIndex = stringBuilder.indexOf(".")) != -1) {
            stringBuilder.deleteCharAt(dotIndex);
        }

        return stringBuilder.toString();
    }
}
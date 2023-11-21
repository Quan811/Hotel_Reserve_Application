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
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Order;
import com.example.hotelbooking.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.MyReserveViewHolder>{

    Context context;
    List<Order> orderList;
    DatabaseReference databaseReference;

    public AdminOrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyReserveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_reserve, parent, false);
        return new MyReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReserveViewHolder holder, int position) {
        Order order = orderList.get(position);

        if(order == null){
            return;
        }
        Accommodation accommodation = order.getAccommodation();
        Room room = order.getRoom();

        holder.accommodationName.setText(accommodation.getAccommodationName());
        holder.location.setText(accommodation.getLocation().trim());

        String total = order.getTotal();
        StringBuilder stringBuilder = new StringBuilder(total).reverse();
        for(int i=3; i<stringBuilder.length(); i+=4){
            stringBuilder.insert(i, ".");
        }
        total = stringBuilder.reverse().toString();
        holder.total.setText(total);
        holder.numberOfRoom.setText(order.getNumberOfRoom());
        holder.roomType.setText(room.getRoomType());
        holder.clientName.setText(order.getClientName());
        holder.phoneNumber.setText(order.getClientPhoneNumber());
        holder.timeOrder.setText(order.getTimeOrder());
        holder.paymentMethod.setText(order.getPaymentMethod());

        String status = order.getOrderStatus();
        Log.d(TAG, "status: "+status);
        if(status.equals("Approved")){
            holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.green));
            holder.orderStatus.setText(status);
        }
        else{
            holder.buttonApproved.setVisibility(View.VISIBLE);
            holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.yellow));
            holder.orderStatus.setText(status);
        }

        Glide.with(holder.roomImg.getContext())
                .load(room.getRoomImg())
                .into(holder.roomImg);

        holder.buttonApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                holder.orderStatus.setText("Approved");
                order.setOrderStatus("Approved");
                updateOrderOnFirebase(order);
                notifyDataSetChanged();
                holder.buttonApproved.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(orderList != null){
            return orderList.size();
        }
        return 0;
    }

    public class MyReserveViewHolder extends RecyclerView.ViewHolder {
        TextView accommodationName, location, numberOfRoom, roomType, total, clientName,
                phoneNumber, timeOrder, paymentMethod, orderStatus;
        ImageView roomImg;
        MaterialButton buttonApproved;
        public MyReserveViewHolder(@NonNull View view) {
            super(view);

            accommodationName = view.findViewById(R.id.accommodation_name);
            location = view.findViewById(R.id.location);
            numberOfRoom = view.findViewById(R.id.number_of_room);
            roomType = view.findViewById(R.id.room_type);
            total = view.findViewById(R.id.total);
            phoneNumber = view.findViewById(R.id.phone_number);
            timeOrder = view.findViewById(R.id.time_order);
            paymentMethod = view.findViewById(R.id.payment_method);
            clientName = view.findViewById(R.id.client_name);
            orderStatus = view.findViewById(R.id.order_status);
            roomImg = view.findViewById(R.id.room_img);
            buttonApproved = view.findViewById(R.id.button_approved);

        }
    }

    private void updateOrderOnFirebase(Order order) {
        // Lấy DatabaseReference cho đối tượng Order cần cập nhật
        databaseReference = FirebaseDatabase.getInstance().getReference("orders").child(order.getOrderID());

        // Cập nhật thuộc tính orderStatus trên Firebase Realtime Database
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("orderStatus", order.getOrderStatus());

        databaseReference.updateChildren(updateData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Update Order on Firebase: Success");
                        } else {
                            Log.e(TAG, "Update Order on Firebase: Failed", task.getException());
                        }
                    }
                });
    }
}

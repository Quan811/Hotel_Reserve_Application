package com.example.hotelbooking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.DetailAccommodationActivity;
import com.example.hotelbooking.model.Accommodation;

import java.util.List;

public class ClientAccommodationsAdapter extends RecyclerView.Adapter<ClientAccommodationsAdapter.LocationDetailViewHolder> {
    Context context;
    List<Accommodation> accommodationList;

    public ClientAccommodationsAdapter(Context context, List<Accommodation> accommodationList) {
        this.context = context;
        this.accommodationList = accommodationList;
    }



    @NonNull
    @Override
    public LocationDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation, parent, false);
        return new LocationDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationDetailViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Accommodation accommodation = accommodationList.get(position);
        if(accommodation == null){
            return;
        }
        holder.accommodationName.setText(accommodation.getAccommodationName());
        holder.accommodationDes.setText(accommodation.getAccommodationDescription());
        holder.accommodationLocation.setText(accommodation.getLocation());
        holder.accommodationRating.setText(accommodation.getAccommodationRating());
        Glide.with(holder.itemImage.getContext())
                .load(accommodation.getThumbnail())
                .centerCrop()
                .into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailAccommodationActivity.class);
                intent.putExtra("accommodation_from_location_to_detail_accommodation",  accommodation);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(accommodationList != null){
            return accommodationList.size();
        }
        return 0;
    }

    public class LocationDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView accommodationName, accommodationDes, accommodationLocation, accommodationRating;

        public LocationDetailViewHolder(@NonNull View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            accommodationName = view.findViewById(R.id.accommodation_name);
            accommodationLocation = view.findViewById(R.id.location);
            accommodationDes = view.findViewById(R.id.accommodation_description);
            accommodationRating = view.findViewById(R.id.accommodation_rating);


        }
    }
}

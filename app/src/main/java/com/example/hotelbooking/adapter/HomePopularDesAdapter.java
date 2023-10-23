package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.PopularDestination;

import java.text.NumberFormat;
import java.util.List;

public class HomePopularDesAdapter extends RecyclerView.Adapter<HomePopularDesAdapter.HomePopularDesViewHolder> {

    Context mContext;
    List<PopularDestination> popularDestinationList;

    public HomePopularDesAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<PopularDestination> list){
        this.popularDestinationList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomePopularDesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_destination,
                parent, false);
        return new HomePopularDesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePopularDesViewHolder holder, int position) {
        PopularDestination popularDestination = popularDestinationList.get(position);
        if(popularDestination == null){
            return;
        }

        holder.popularDestinationImg.setImageResource(popularDestination.getResourceImg());
        holder.popularDesinationName.setText(popularDestination.getPopularDesinationName());
        NumberFormat numberFormat = NumberFormat.getInstance();
        holder.numberAccomodation.setText(numberFormat.format(popularDestination.getNumberAccomodation()));
    }

    @Override
    public int getItemCount() {
        if(popularDestinationList != null){
            return popularDestinationList.size();
        }
        return 0;
    }


    public class HomePopularDesViewHolder extends RecyclerView.ViewHolder {
        ImageView popularDestinationImg;
        TextView popularDesinationName, numberAccomodation;
        public HomePopularDesViewHolder(@NonNull View view) {
            super(view);
            popularDesinationName = view.findViewById(R.id.popular_destination_name);
            popularDestinationImg = view.findViewById(R.id.popular_destination_img);
            numberAccomodation = view.findViewById(R.id.number_accomodation);
        }
    }
}

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
import com.example.hotelbooking.model.PropertyType;

import java.util.List;

public class HomePropertyTypeAdapter extends RecyclerView.Adapter<HomePropertyTypeAdapter.PropertyTypeViewHolder> {

    Context mContext;
    List<PropertyType> propertyTypeList;

    public HomePropertyTypeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<PropertyType> list){
        this.propertyTypeList = list;
    }

    @NonNull
    @Override
    public PropertyTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_type, parent, false);
        return new PropertyTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyTypeViewHolder holder, int position) {
        PropertyType propertyType = propertyTypeList.get(position);
        if(propertyType == null){
            return;
        }

        holder.propertyTypeImage.setImageResource(propertyType.getResourceImg());
        holder.propertyTypeName.setText(propertyType.getPropertyTypeName());

    }

    @Override
    public int getItemCount() {
        if(propertyTypeList != null){
            return propertyTypeList.size();
        }
        return 0;
    }

    public class PropertyTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView propertyTypeImage;
        TextView propertyTypeName;

        public PropertyTypeViewHolder(@NonNull View view) {

            super(view);

            propertyTypeImage = view.findViewById(R.id.property_type_img);
            propertyTypeName = view.findViewById(R.id.property_type_name);
        }
    }
}

package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Photo;

import java.util.List;

public class DetailAccommodationAdapter extends PagerAdapter {
    Context context;
    List<String> list;

    public DetailAccommodationAdapter(Context context, List<String> listImg) {
        this.context = context;
        this.list = listImg;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slide_image_acc_detail, container, false);
        ImageView imgView = view.findViewById(R.id.imgView);
        Glide.with(imgView.getContext()).load(list.get(position)).centerCrop().into(imgView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

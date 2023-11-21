package com.example.hotelbooking.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Room;

import java.util.List;

public class ImageURLAdapter extends RecyclerView.Adapter<ImageURLAdapter.ImageURLViewHolder> {
    Context context;
    List<String> listURL;
    public ImageURLAdapter(Context context, List<String> listURL) {
        this.listURL = listURL;
        this.context = context;
    }
    public List<String> getListURL(){
        return listURL;
    }

    @NonNull
    @Override
    public ImageURLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_url, parent, false);
        return new ImageURLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageURLViewHolder holder, int position) {
        String url = listURL.get(position);
        if(url != null){
            holder.edtURL.setText(url);

        }
        holder.buttonDelete.setOnClickListener(v -> {
            listURL.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return listURL.size();
    }

    public class ImageURLViewHolder extends RecyclerView.ViewHolder{
        EditText edtURL;
        ImageButton buttonDelete;
        public ImageURLViewHolder(@NonNull View view) {
            super(view);
            edtURL = view.findViewById(R.id.edt_URL);
            buttonDelete = view.findViewById(R.id.button_delete);
        }
    }
}

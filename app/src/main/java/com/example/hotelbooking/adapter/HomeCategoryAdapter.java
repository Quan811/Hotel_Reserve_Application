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
import com.example.hotelbooking.model.Category;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>{
    Context context;
    private List<Category> listCategory;

    public HomeCategoryAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<Category> list){
        this.listCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = listCategory.get(position);
        if (category == null){
            return;
        }

        holder.categoryImage.setImageResource(category.getResourceImg());
        holder.categoryName.setText(category.getCategoryName());

    }

    @Override
    public int getItemCount() {
        if(listCategory != null){
            return listCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryName;

        public CategoryViewHolder(@NonNull View view) {
            super(view);

            categoryImage = view.findViewById(R.id.category_img);
            categoryName = view.findViewById(R.id.category_name);
        }
    }

}

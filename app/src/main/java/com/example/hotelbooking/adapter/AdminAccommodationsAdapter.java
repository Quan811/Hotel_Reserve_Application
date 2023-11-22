package com.example.hotelbooking.adapter;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.AdminUpdateAccommodationActivity;
import com.example.hotelbooking.model.Accommodation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminAccommodationsAdapter extends RecyclerView.Adapter<AdminAccommodationsAdapter.AccommodationsViewHolder> {

    Context context;
    List<Accommodation> accommodationList;

    DatabaseReference databaseReference;

    public AdminAccommodationsAdapter(Context context, List<Accommodation> accommodationList) {
        this.context = context;
        this.accommodationList = accommodationList;
    }

    @NonNull
    @Override
    public AccommodationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_accommodation, parent, false);
        return new AccommodationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationsViewHolder holder, int position) {
        Accommodation accommodation = accommodationList.get(position);
        if(accommodation == null){
            return;
        }
        holder.accommodationName.setText(accommodation.getAccommodationName());
        holder.accommodationLocation.setText(accommodation.getLocation());
        holder.accommodationRating.setText(accommodation.getAccommodationRating());
        Glide.with(holder.accommodationImage.getContext())
                .load(accommodation.getThumbnail())
                .centerCrop()
                .into(holder.accommodationImage);

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), AdminUpdateAccommodationActivity.class);
                intent.putExtra("accommodation_to_update_activity",accommodation);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.accommodationImage.getContext());

                builder.setTitle("WARNING!!!");
                builder.setMessage("Can not undo after delete, continue?");

                // Nút Yes
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện xóa ở đây
                        databaseReference = FirebaseDatabase.getInstance().getReference("accommodations");
                        databaseReference.child(accommodation.getAccommodationName())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "Delete Accommodation:  Success ");
                                        }else {
                                            Log.d(TAG, "Delete Accommodation:  Failed!!! ");
                                        }
                                    }
                                });
                    }
                });

                // Nút No
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog, không thực hiện xóa
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return accommodationList.size();
    }

    public class AccommodationsViewHolder extends RecyclerView.ViewHolder{
        ImageView accommodationImage;
        TextView accommodationName, accommodationDes, accommodationLocation, accommodationRating;
        MaterialButton buttonUpdate, buttonDelete;

        public AccommodationsViewHolder(@NonNull View view) {
            super(view);
            accommodationImage = view.findViewById(R.id.accommodation_image);
            accommodationName = view.findViewById(R.id.accommodation_name);
            accommodationLocation = view.findViewById(R.id.location);
            accommodationDes = view.findViewById(R.id.accommodation_description);
            accommodationRating = view.findViewById(R.id.accommodation_rating);
            buttonUpdate = view.findViewById(R.id.button_update);
            buttonDelete = view.findViewById(R.id.button_delete);
        }
    }


}

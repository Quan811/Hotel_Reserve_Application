package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.hotelbooking.R;

public class OnboardingAdapter extends PagerAdapter {

    Context context;
    int images[] = {
            R.drawable.onboarding_1,
            R.drawable.onboarding_2,
            R.drawable.onboarding_3
    };

    int title[] = {
            R.string.onboarding_title_1,
            R.string.onboarding_title_2,
            R.string.onboarding_title_3,
    };

    int description[] = {
            R.string.description_1,
            R.string.description_2,
            R.string.description_3
    };

    public OnboardingAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_onboarding_screen, container, false);

        ImageView slideTitleImage = view.findViewById(R.id.titleImage);
        TextView slideTitle = view.findViewById(R.id.title);
        TextView slideDescription = view.findViewById(R.id.description);

        slideTitleImage.setImageResource(images[position]);
        slideTitle.setText(title[position]);
        slideDescription.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}

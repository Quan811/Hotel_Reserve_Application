package com.example.hotelbooking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import me.relex.circleindicator.CircleIndicator;

import com.example.hotelbooking.adapter.OnboardingAdapter;
import com.example.hotelbooking.R;
import com.google.android.material.button.MaterialButton;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    MaterialButton getStartedButton;
    CircleIndicator circleIndicator;
    OnboardingAdapter onboardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra trạng thái đã hoàn thành từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isOnboardingCompleted = preferences.getBoolean("completed", false);

        if (isOnboardingCompleted) {
            // Người dùng đã hoàn thành Onboarding, chuyển đến màn hình chính và kết thúc màn hình Onboarding
            Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Màn hình Onboarding sẽ hiển thị
            setContentView(R.layout.activity_onboarding_screen);

            getStartedButton = findViewById(R.id.get_started_button);
            slideViewPager = findViewById(R.id.slide_viewpager);
            circleIndicator = findViewById(R.id.circle_indicator);

            getStartedButton.setVisibility(View.INVISIBLE);
            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lưu trạng thái đã hoàn thành vào SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("completed", true);
                    editor.apply();

                    Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            onboardingAdapter = new OnboardingAdapter(this);
            slideViewPager.setAdapter(onboardingAdapter);
            slideViewPager.addOnPageChangeListener(viewListener);

            circleIndicator.setViewPager(slideViewPager);
            onboardingAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 2) {
                getStartedButton.setVisibility(View.VISIBLE);
            } else {
                getStartedButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

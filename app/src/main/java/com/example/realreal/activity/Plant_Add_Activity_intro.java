package com.example.realreal.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.realreal.R;

public class Plant_Add_Activity_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_add_intro);

        LottieAnimationView animationView = (LottieAnimationView)findViewById(R.id.second_animation);
        animationView.setAnimation("love_plant.json");
        animationView.playAnimation();
    }
}

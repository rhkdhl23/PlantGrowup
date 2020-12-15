package com.example.realreal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.realreal.Intro_MainActivity;
import com.example.realreal.R;

public class Loading_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.loading_animation);
        animationView.setAnimation("loading.json");
        animationView.playAnimation();

        startLoading();
    }

    //app 실행했을 때, 실행되는 로딩 화면 - json 파일의 Lottie Animation으로 걸어줌
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            //Loding Animation 종료 후 시작 화면 자동으로 넘어감
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(), Intro_MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); //Lottie Loding 2초동안 발사!!
    }
}

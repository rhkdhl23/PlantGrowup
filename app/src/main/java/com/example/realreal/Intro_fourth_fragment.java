package com.example.realreal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

public class Intro_fourth_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;

    public Intro_fourth_fragment() {
        // Required empty public constructor
    }

    public static Intro_fourth_fragment newInstance(String param1, String param2, String Param3, String Param4, String Param5) {
        Intro_fourth_fragment fragment = new Intro_fourth_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, Param3);
        args.putString(ARG_PARAM4, Param4);
        args.putString(ARG_PARAM5, Param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro_fourth, container, false);

        LottieAnimationView animationView = (LottieAnimationView) v.findViewById(R.id.fourth_animation);
        animationView.setAnimation("mobile_notification.json");
        animationView.playAnimation();

        return v;

    }
}

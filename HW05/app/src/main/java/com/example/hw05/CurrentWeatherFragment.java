package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentWeatherFragment extends Fragment {
    String city;
    ImageView imageViewweather;
    TextView textViewTemprature,textViewTempratureMax,textViewTempratureMin,textViewDescription,textViewHumidity,textViewWindSpeed;
    TextView textViewWindDegree, textViewCloudiness;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_weather, container, false);

        v.findViewById(R.id.button_forecast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoWeatherForecastFragment(city);
            }
        });

        return  v;
    }
    //https://api.openweathermap.org/data/2.5/weather?q=Charlotte,US&units=imperial&appid=8db9d45b1601ec9cd28a10ca0e154a91
    public void setPosition(String city) {
        this.city = city;
    }
    CurrentWeatherFragment.CurrentWeatherListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CurrentWeatherFragment.CurrentWeatherListener)(context);
    }

    interface CurrentWeatherListener{
        void gotoWeatherForecastFragment(String city);
    }

}
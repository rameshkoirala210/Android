package com.example.hw05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherForecastFragment extends Fragment {
    private static String TAG = "TAGforecast";
    String city;
    TextView textViewforcastCity;
    RecyclerView Recyclerviewforecast;
    LinearLayoutManager layoutManager;
    WeatherForecastRecycler adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        getActivity().setTitle("Weather Forcast");

        textViewforcastCity = v.findViewById(R.id.textViewforcastCity);
        textViewforcastCity.setText(city);

        Recyclerviewforecast = v.findViewById(R.id.Recyclerviewforecast);
        Recyclerviewforecast.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        Recyclerviewforecast.setLayoutManager(layoutManager);

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city +"&units=imperial&appid=8db9d45b1601ec9cd28a10ca0e154a91";
        adapter = new WeatherForecastRecycler(url);
        Recyclerviewforecast.setAdapter(adapter);
        Log.d(TAG, "onCreateView: ssssss");


        return  v;
    }

    public void setPosition(String city) {
        this.city = city;
    }
}
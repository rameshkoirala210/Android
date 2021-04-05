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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/*
    Assignment # Homework-05
    File Name Weather Forecast Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class WeatherForecastFragment extends Fragment {
    private static String TAG = "TAGforecast";
    ArrayList<Weather> weathers = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
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
        getWeathers(url);
        adapter = new WeatherForecastRecycler(weathers);
        Recyclerviewforecast.setAdapter(adapter);
        Log.d(TAG, "onCreateView: ssssss");


        return  v;
    }

    public void setPosition(String city) {
        this.city = city;
    }

    void getWeathers(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: " + request.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: INSIDE RESPONSE");
                if (response.isSuccessful()) {
                    try {
                        JSONObject weatherJsonObject = new JSONObject(response.body().string());
                        JSONArray forcast = weatherJsonObject.getJSONArray("list");
                        Log.d(TAG, "onResponse: JSON FORCAST");
                        for(int i = 0;i<forcast.length();i++){
                            JSONObject weekforecast = forcast.getJSONObject(i);
                            Weather weather = new Weather();

                            JSONObject main = weekforecast.getJSONObject("main");
                            weather.setTemprature(main.getString("temp"));
                            weather.setTempratureMax(main.getString("temp_max"));
                            weather.setTempratureMin(main.getString("temp_min"));
                            weather.setHumidity(main.getString("humidity"));

                            JSONArray weatherArray = weekforecast.getJSONArray("weather");
                            JSONObject weatherDescription = weatherArray.getJSONObject(0);
                            weather.setDescription(weatherDescription.getString("description"));
                            weather.setIcon(weatherDescription.getString("icon"));

                            weather.setDate(weekforecast.getString("dt_txt"));
                            weathers.add(weather);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new WeatherForecastRecycler(weathers);
                                Recyclerviewforecast.setAdapter(adapter);
                                Log.d(TAG, "onCreateView: ssssss");
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "out array: "+ weathers);
            }
        });
    }


}
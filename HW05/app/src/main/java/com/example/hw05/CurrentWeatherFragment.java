package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/*
    Assignment # Homework-05
    File Name Current Weather Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class CurrentWeatherFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    Weather weather = new Weather();
    String city,url;
    ImageView imageViewweather;
    TextView textViewTemprature,textViewTempratureMax,textViewTempratureMin,textViewDescription,textViewHumidity,textViewWindSpeed;
    TextView textViewWindDegree, textViewCloudiness, textViewSelectedCity;


    final String TAG = "TAGCW";
    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_weather, container, false);
        getActivity().setTitle("Current Weather");

        imageViewweather = v.findViewById(R.id.imageViewweather);
        textViewTemprature = v.findViewById(R.id.textViewTemprature);
        textViewTempratureMax = v.findViewById(R.id.textViewTempratureMax);
        textViewTempratureMin = v.findViewById(R.id.textViewTempratureMin);
        textViewCloudiness = v.findViewById(R.id.textViewCloudiness);
        textViewDescription = v.findViewById(R.id.textViewDescription);
        textViewHumidity = v.findViewById(R.id.textViewHumidity);
        textViewWindSpeed = v.findViewById(R.id.textViewWindSpeed);
        textViewWindDegree = v.findViewById(R.id.textViewWindDegree);
        textViewSelectedCity = v.findViewById(R.id.textViewSelectedCity);

        textViewSelectedCity.setText(city);

        v.findViewById(R.id.button_forecast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoWeatherForecastFragment(city);
            }
        });

        url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=imperial&appid=8db9d45b1601ec9cd28a10ca0e154a91";
        getWeather(url);


        return  v;
    }
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
    void getWeather(String url){
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
                Weather weather = new Weather();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: response successful");
                    try {
                        JSONObject weatherJsonObject = new JSONObject(response.body().string());
                        Log.d(TAG, "onResponse: " + weatherJsonObject.toString());

                        JSONObject main = weatherJsonObject.getJSONObject("main");
                        weather.setTemprature(main.getString("temp"));
                        weather.setTempratureMax(main.getString("temp_max"));
                        weather.setTempratureMin(main.getString("temp_min"));
                        weather.setHumidity(main.getString("humidity"));

                        JSONArray weatherArray = weatherJsonObject.getJSONArray("weather");
                        JSONObject weatherDescription = weatherArray.getJSONObject(0);
                        weather.setDescription(weatherDescription.getString("description"));
                        weather.setIcon(weatherDescription.getString("icon"));

                        JSONObject wind = weatherJsonObject.getJSONObject("wind");
                        weather.setWindSpeed(wind.getString("speed"));
                        weather.setWindDegree(wind.getString("deg"));

                        JSONObject clouds = weatherJsonObject.getJSONObject("clouds");
                        weather.setCloudiness(clouds.getString("all"));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewTemprature.setText(weather.temprature + "F");
                                textViewTempratureMax.setText(weather.tempratureMax + "F");
                                textViewTempratureMin.setText(weather.tempratureMin + "F");
                                textViewDescription.setText(weather.description);
                                textViewHumidity.setText(weather.humidity + "%");
                                textViewWindSpeed.setText(weather.windSpeed + " miles/hr ");
                                textViewWindDegree.setText(weather.windDegree + " degrees ");
                                textViewCloudiness.setText(weather.cloudiness + "%");


                                String imgUrl = "https://openweathermap.org/img/wn/" + weather.icon +"@2x.png";
                                Log.d(TAG, "onCreateView: " + imgUrl);
                                Picasso.get().load(imgUrl).into(imageViewweather);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "out array: "+ weather);
            }
        });
    }
}
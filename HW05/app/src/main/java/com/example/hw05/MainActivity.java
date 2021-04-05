package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
    Assignment # Homework-05
    File Name  Main Activity
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class MainActivity extends AppCompatActivity implements CitiesListFragment.CitiesListListener,CurrentWeatherFragment.CurrentWeatherListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout, new CitiesListFragment()).commit();
    }

    @Override
    public void gotocurrentweatherfragment(String city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragment.setPosition(city);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack(null).commit();
    }

    @Override
    public void gotoWeatherForecastFragment(String city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        fragment.setPosition(city);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack(null).commit();
    }
}
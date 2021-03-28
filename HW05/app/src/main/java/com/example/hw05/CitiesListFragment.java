package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hw05.Data;

import java.util.ArrayList;

public class CitiesListFragment extends Fragment {
    final String TAG = "TAGcity";
    ListView listViewCities;
    ArrayList<Data.City> cities = Data.cities;
    public CitiesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cities_list, container, false);
        getActivity().setTitle("Cities");

        listViewCities = v.findViewById(R.id.listViewCities);

        ArrayAdapter<Data.City> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cities);
        listViewCities.setAdapter(adapter);

        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = String.valueOf(cities.get(position));
                Log.d(TAG , "onItemClick: " + city);
                mListener.gotocurrentweatherfragment(city);
            }
        });
        return v;
    }
    CitiesListFragment.CitiesListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CitiesListListener)(context);
    }

    interface CitiesListListener{
        void gotocurrentweatherfragment(String city);
    }
}
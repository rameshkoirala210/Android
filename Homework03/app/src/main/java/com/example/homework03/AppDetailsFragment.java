package com.example.homework03;

/*
    Assignment # Homework #03
    File Name App Details Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AppDetailsFragment extends Fragment {

    DataServices.App app;
    TextView appName, artistName, releaseDate;
    RecyclerView recyclerview_genres;
    LinearLayoutManager layoutManager;
    AppDetailsRecyclerAdapter adapter;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Apps Detail");
        View view =  inflater.inflate(R.layout.fragment_app_details, container, false);
        appName = view.findViewById(R.id.appDetailsName);
        artistName = view.findViewById(R.id.appDetailsArtist);
        releaseDate = view.findViewById(R.id.appDetailsDate);
        recyclerview_genres = view.findViewById(R.id.recyclerview_genres);
        recyclerview_genres.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview_genres.setLayoutManager(layoutManager);

        if (app!=null){
            appName.setText(app.name);
            artistName.setText(app.artistName);
            releaseDate.setText(app.releaseDate);

            adapter = new AppDetailsRecyclerAdapter(app.genres);
            recyclerview_genres.setAdapter(adapter);
        }

        return view;
    }

    public void setApp(DataServices.App app) {
        this.app = app;
    }
}
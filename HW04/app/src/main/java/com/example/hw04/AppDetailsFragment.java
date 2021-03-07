package com.example.hw04;
/*
    Assignment # In Class Assignment 05
    File Name App Details Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AppDetailsFragment extends Fragment {

    DataServices.App app;
    TextView appName, artistName, releaseDate;
    ListView listView_genres;

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
        listView_genres = view.findViewById(R.id.listview_genres);

        if (app!=null){
            appName.setText(app.name);
            artistName.setText(app.artistName);
            releaseDate.setText(app.releaseDate);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, app.genres);
            listView_genres.setAdapter(adapter);
        }

        return view;
    }

    public void setApp(DataServices.App app) {
        this.app = app;
    }
}
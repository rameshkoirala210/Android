package com.example.hw04;
/*
    Assignment # Homework 04
    File Name AppAdepter
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppAdapter extends ArrayAdapter<DataServices.App> {
    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> apps) {
        super(context, resource, apps);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_item, parent, false);
        }
        DataServices.App app = getItem(position);
        TextView appName = convertView.findViewById(R.id.textViewApp);
        TextView artistName = convertView.findViewById(R.id.textViewArtist);
        TextView releaseDate = convertView.findViewById(R.id.textViewRelease);

        appName.setText(app.name);
        artistName.setText(app.artistName);
        releaseDate.setText(app.releaseDate);

        return convertView;
    }
}

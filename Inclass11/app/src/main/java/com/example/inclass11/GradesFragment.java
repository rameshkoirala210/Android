package com.example.inclass11;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GradesFragment extends Fragment {
    TextView textViewGPA, textViewHours;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        textViewGPA = view.findViewById(R.id.textViewGPA);
        textViewHours = view.findViewById(R.id.textViewHours);

        view.findViewById(R.id.buttonAddCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
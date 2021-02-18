package com.example.inclass04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    TextView sentName;
    Button editProfile, logout;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_account, container, false);

        sentName = (TextView)v.findViewById(R.id.accountName);
        logout = (Button) v.findViewById(R.id.buttonLogout);
        editProfile = (Button) v.findViewById(R.id.buttonProfile);



        return v;
    }
}
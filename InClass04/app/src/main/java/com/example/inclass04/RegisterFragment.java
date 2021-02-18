package com.example.inclass04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterFragment extends Fragment {

    EditText registeremail,registerpassword,registerName;
    Button registerSubmit,registerCalcel;

    public RegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Register");

        registeremail = (EditText)v.findViewById(R.id.RegisterEmail);
        registerpassword = (EditText)v.findViewById(R.id.RegisterPassword);
        registerName = (EditText)v.findViewById(R.id.RegisterName);
        registerSubmit = (Button) v.findViewById(R.id.buttonSubmit);
        registerCalcel = (Button) v.findViewById(R.id.buttonCancle);



        return v;
    }
}
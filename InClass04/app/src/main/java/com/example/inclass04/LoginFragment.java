package com.example.inclass04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LoginFragment extends Fragment {

    EditText email,password;
    Button login,newAccount;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText)v.findViewById(R.id.RegisterEmail);
        password = (EditText)v.findViewById(R.id.UpdatePassword);
        login = (Button) v.findViewById(R.id.buttonLogin);
        newAccount = (Button) v.findViewById(R.id.buttonNewAccount);



        return v;
    }
}
package com.example.inclass04;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    EditText loginEmail,loginpassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginEmail = (EditText)v.findViewById(R.id.LoginEmail);
        loginpassword = (EditText)v.findViewById(R.id.LoginPassword);

        v.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  loginEmail.getText().toString();
                String password = loginpassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter Login/Password!!", Toast.LENGTH_SHORT).show();
                }else{
                    DataServices.Account account = DataServices.login(email,password);
                    if (account == null){
                        Toast.makeText(getActivity(), "Unable to Login!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Login Succesful!!", Toast.LENGTH_SHORT).show();
                        //getFragmentManager().beginTransaction().replace(R.id.layout, AccountFragment.newInstance(account)).commit();
                        mListner.setAccountGoToAccountFragment(account);
                    }
                }
            }
        });
        v.findViewById(R.id.buttonNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.layout, new RegisterFragment()).commit();
            }
        });

        return v;
    }

    LoginListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (LoginListener)(context);
    }

    interface LoginListener{
        void setAccountGoToAccountFragment(DataServices.Account account);
    }
}
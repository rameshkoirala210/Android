package com.example.hw04;
/*
    Assignment # In Class Assignment 05
    File Name Login Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private static final String TAG = "TAG_LOGIN";
    EditText loginEmail, loginpassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");

        loginEmail = (EditText) v.findViewById(R.id.LoginEmail);
        loginpassword = (EditText) v.findViewById(R.id.LoginPassword);

        v.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  loginEmail.getText().toString();
                String password = loginpassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), "Please Enter Email/Password!!", Toast.LENGTH_SHORT).show();
                }else{
                    new doAsyncTaskLogin().execute(email, password);
//                    DataServices.login(email, password, new DataServices.AuthResponse() {
//                        @Override
//                        public void onSuccess(String token) {
//                            Toast.makeText(getActivity(), "Login Succesful!!", Toast.LENGTH_SHORT).show();
//                            //getFragmentManager().beginTransaction().replace(R.id.layout, AccountFragment.newInstance(account)).commit();
//                            mListner.goToAppCategoryFragment(token);
//                        }
//
//                        @Override
//                        public void onFailure(DataServices.RequestException exception) {
//                            Toast.makeText(getActivity(), "Unable to Login!!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

        v.findViewById(R.id.buttonNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment fragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
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
        void goToAppCategoryFragment(String token);
    }

    class doAsyncTaskLogin extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];
            String token = "";
            try {
                token = DataServices.login(email, password);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return token;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty()){
                Toast.makeText(getActivity(), "Unable to Login!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick:notable");
            }else{
                Toast.makeText(getActivity(), "Login Succesful!!", Toast.LENGTH_SHORT).show();
                mListner.goToAppCategoryFragment(s);
            }
        }
    }
}
package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/*
    Assignment # Homework 04
    File Name Register Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class RegisterFragment extends Fragment {
    private static final String TAG = "TAG_Register";
    EditText registeremail,registerpassword,registerName;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("Register");

        registeremail = (EditText)v.findViewById(R.id.RegisterEmail);
        registerpassword = (EditText)v.findViewById(R.id.registerpassword);
        registerName = (EditText)v.findViewById(R.id.RegisterName);

        v.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  registeremail.getText().toString();
                String password = registerpassword.getText().toString();
                String name = registerName.getText().toString();
                if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                    Toast.makeText(getActivity(), "Please Fill in  Name/Login/Password!!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getActivity(), "EMAIL incorrect", Toast.LENGTH_SHORT).show();
                }  else{
                        new doAsyncTaskRegister().execute(name,email, password);
                }
            }
        });
        v.findViewById(R.id.buttonCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment fragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
            }
        });
        return v;
    }

    LoginFragment.LoginListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (LoginFragment.LoginListener)(context);
    }

    class doAsyncTaskRegister extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String name = strings[0];
            String email = strings[1];
            String password = strings[2];
            String token = "";
            try {
                token = DataServices.register(name,email,password);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return token;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty()){
                Toast.makeText(getActivity(), "Unable to Register!!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Login Succesful!!", Toast.LENGTH_SHORT).show();
                mListner.goToAppCategoryFragment(s);
            }
        }
    }
}
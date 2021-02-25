package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
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
                    DataServices.register(name, email, password, new DataServices.AuthResponse() {
                        @Override
                        public void onSuccess(String token) {
                            Toast.makeText(getActivity(), "Registration Succesful!!", Toast.LENGTH_SHORT).show();
                            mListner.goToAppCategoryFragment(token);
                        }
                        @Override
                        public void onFailure(DataServices.RequestException exception) {
                            Toast.makeText(getActivity(), "Unable to Register!!", Toast.LENGTH_SHORT).show();
                        }
                    });
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
}
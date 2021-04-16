package com.example.hw07;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    EditText loginEmail, loginPassword;

    private final String TAG = "logintag" ;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Login");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginEmail = view.findViewById(R.id.LoginEmail);
        loginPassword = view.findViewById(R.id.LoginPassword);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if(email.isEmpty()) {
                    builder.setTitle("Missing Fields").
                            setMessage("Email is Empty")
                            .setPositiveButton("OK", null)
                            .show();
                }else if(password.isEmpty()){
                    builder.setTitle("Missing Fields").
                            setMessage("Password is Empty")
                            .setPositiveButton("OK", null)
                            .show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mListener.gotoProfileFragmentfromLogin();
                            }else{
                                builder.setTitle("Not Successful")
                                        .setMessage(task.getException().getMessage())
                                        .setPositiveButton("OK", null)
                                        .show();
                                //Log.d(TAG, "onComplete: " + task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
        view.findViewById(R.id.buttonNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoNewAccountFragment();
            }
        });
        return view;
    }
    LoginFragment.LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginListener) (context);
    }

    interface LoginListener{
        void gotoProfileFragmentfromLogin();
        void gotoNewAccountFragment();
    }
}
package com.example.inclass04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UpdateAccountFragment extends Fragment {
    TextView TextViewemail;
    EditText updateName,updatePassword;
    Button submit,cancel;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_account, container, false);

        TextViewemail = (TextView) v.findViewById(R.id.TextViewemail);
        updateName = (EditText)v.findViewById(R.id.UpdateName);
        updatePassword = (EditText)v.findViewById(R.id.UpdatePassword);
        submit = (Button) v.findViewById(R.id.UpdateSubmit);
        cancel = (Button) v.findViewById(R.id.UpdateCancle);




        return v;
    }
}
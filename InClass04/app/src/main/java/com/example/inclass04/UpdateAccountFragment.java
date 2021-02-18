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
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";
    TextView TextViewemail;
    EditText updateName,updatePassword;
    Button submit,cancel;

    private DataServices.Account mAccount;
    public UpdateAccountFragment() {
        // Required empty public constructor
    }
    public static AccountFragment newInstance(DataServices.Account account){
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccount = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
        }
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
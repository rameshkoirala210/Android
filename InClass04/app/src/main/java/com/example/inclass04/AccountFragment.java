package com.example.inclass04;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";
    TextView sentName;
    Button editProfile, logout;

    private DataServices.Account mAccount;
    public AccountFragment() {
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
       View v = inflater.inflate(R.layout.fragment_account, container, false);

        sentName = (TextView)v.findViewById(R.id.accountName);
        logout = (Button) v.findViewById(R.id.buttonLogout);
        editProfile = (Button) v.findViewById(R.id.buttonProfile);

        sentName.setText(this.mAccount.getName());


        return v;
    }
}
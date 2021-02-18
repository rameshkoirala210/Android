package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";
    TextView sentName;
    private static final String TAG = "TAG_Account";

    private DataServices.Account mAccount;
    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(DataServices.Account account){
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: ");
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
        getActivity().setTitle("Account");

        sentName = (TextView)v.findViewById(R.id.accountName);
        sentName.setText(this.mAccount.getName());
        Log.d("TAG", "onCreateView: " + mAccount.getName() + mAccount.getEmail());


        v.findViewById(R.id.buttonProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "clicked update");
                mListner.goToUpdateAccountFragment(mAccount);
            }
        });
        v.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
    AccountListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (AccountListener)(context);
    }

    interface AccountListener{
        void goToUpdateAccountFragment(DataServices.Account account);
    }
}
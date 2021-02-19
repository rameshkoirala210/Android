package com.example.inclass04;

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
import android.widget.TextView;
import android.widget.Toast;


public class UpdateAccountFragment extends Fragment {
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";
    private static final String TAG = "TAG_Update";
    TextView TextViewemail;
    EditText updateName,updatePassword;

    private DataServices.Account mAccount;
    public UpdateAccountFragment() {
        // Required empty public constructor
    }
    public static UpdateAccountFragment newInstance(DataServices.Account account){
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        Log.d(TAG, "IN newInstance of UpdateAccountFragment");
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

        TextViewemail.setText(this.mAccount.getEmail());
        v.findViewById(R.id.UpdateSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =  updateName.getText().toString();
                String password = updatePassword.getText().toString();
                if(name.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), "Name/Password is Empty!!", Toast.LENGTH_SHORT).show();
                }else{
                    mAccount = DataServices.update(mAccount, name, password);
                    if (mAccount != null) {
                        mListner.updatedNewAccount(mAccount);
                        Log.d(TAG, "Onclick: " + mAccount.getName());
                    } else if (mAccount == null) {
                        Toast.makeText(getActivity(), "Account Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        v.findViewById(R.id.UpdateCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }
    UpdateAccountListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (UpdateAccountListener)(context);
    }

    interface UpdateAccountListener{
        void updatedNewAccount(DataServices.Account account);
    }
}
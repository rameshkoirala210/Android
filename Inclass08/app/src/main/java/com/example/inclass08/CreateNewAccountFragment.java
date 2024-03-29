package com.example.inclass08;

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

public class CreateNewAccountFragment extends Fragment {
    EditText registerName, registerEmail,registerpassword;
    private FirebaseAuth mAuth;
    private final String TAG = "newAccounttag" ;

    public CreateNewAccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        registerName = view.findViewById(R.id.RegisterName);
        registerEmail = view.findViewById(R.id.RegisterEmail);
        registerpassword = view.findViewById(R.id.registerpassword);
        mAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerpassword.getText().toString();

                if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                    Toast.makeText(getActivity(), "Its Empty", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "onComplete: Successful");

                                        Log.d(TAG, "onComplete: " + mAuth.getCurrentUser().getUid());
                                        mListener.gotoFourmsFragmentfromRegister(registerName.getText().toString());

                                    }else{
                                        Log.d(TAG, "onComplete: error");
                                        Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                    }

                                }
                            });
                }

            }
        });
        view.findViewById(R.id.buttonCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoLoginFragment();
            }
        });

        return view;
    }
    CreateNewAccountFragment.NewAccountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewAccountListener) (context);
    }

    interface NewAccountListener{
        void gotoFourmsFragmentfromRegister(String name);
        void gotoLoginFragment();
    }
}
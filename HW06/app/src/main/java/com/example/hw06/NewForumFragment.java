package com.example.hw06;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class NewForumFragment extends Fragment {
    EditText editForumTitle, editForumDescription;
    FirebaseAuth mAuth;
    private final String TAG = "TAG_NewForum";

    public NewForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("New Forum");
        mAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);
        editForumTitle = view.findViewById(R.id.editForumTitle);
        editForumDescription = view.findViewById(R.id.editForumDescription);

        //setData(title, description);
        view.findViewById(R.id.buttonSubmitForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editForumTitle.getText().toString().isEmpty() && editForumDescription.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Its Empty", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onClick: xxdfasdf");
                    setData(editForumTitle.getText().toString(), editForumDescription.getText().toString());
                }
            }
        });

        view.findViewById(R.id.buttonCancleForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doneCreateForum();
            }
        });

        return view;
    }

    NewForumFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (NewForumFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreateForumListener");
        }
    }

    interface NewForumFragmentListener{
        void doneCreateForum();
    }
    private void setData(String title, String description){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> user = new HashMap<>();
        user.put("UUID", mAuth.getCurrentUser().getUid());
        user.put("createdbyName", mAuth.getCurrentUser().getDisplayName());
        user.put("datetime", Timestamp.now());
        user.put("description", description);
        user.put("likedBy", new ArrayList<>());
        user.put("title",title);
        Log.d(TAG, "setData: " + "TESTSTSTSEDT");
        db.collection("forums")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: xxxxx" );
                        user.put("documentID", documentReference.getId());
                        documentReference.update(user);
                        mListener.doneCreateForum();
                    }
                });


    }
}
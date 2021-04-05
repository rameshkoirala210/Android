package com.example.hw06;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class NewForumFragment extends Fragment {
    String name;
    EditText editForumTitle, editForumDescription;
    public NewForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);
        editForumTitle = view.findViewById(R.id.editForumTitle);
        editForumDescription = view.findViewById(R.id.editForumDescription);

        String title = editForumTitle.getText().toString();
        String description = editForumDescription.getText().toString();

        if(title.isEmpty() || description.isEmpty()){
            Toast.makeText(getContext(), "Its Empty", Toast.LENGTH_SHORT).show();
        }else{
            setData(title, description, name);
        }

        return view;
    }

    private void setData(String title, String description, String name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> user = new HashMap<>();
        user.put("title",title);
        user.put("description", description);
        user.put("createdbyName", name);
        user.put("datetime", Timestamp.now());
        db.collection("datasetforForums");


        db.collection("datasetforForums").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });


    }
    public void setname(String name) {
        this.name = name;
    }
}
package com.example.hw07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileListFragment extends Fragment {
    final String TAG = "TAG_ProfileList";
    ArrayList<Profile> profiles = new ArrayList<>();
    ArrayList<String> profileNames = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ListView profileListView;
    ArrayAdapter adapter;
    public ProfileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile_list, container, false);
        getActivity().setTitle("Select Profile");
        profileListView = view.findViewById(R.id.profileListView);
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, profileNames);
        getProfiles();
        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.gotoProfileFragment(profiles.get(position));
            }
        });
        profileListView.setAdapter(adapter);
        view.findViewById(R.id.buttonLogoutProfileList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mListener.gotoLoginFragmentafterLogout();
            }
        });
        return view;
    }

    private void getProfiles(){
        db.collection("Profile").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    profiles.clear();
                    profileNames.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: value) {
                        Profile profile = queryDocumentSnapshot.toObject(Profile.class);
                        profiles.add(profile);
                        profileNames.add(profile.getName());
                    }
                    Log.d(TAG, "onEvent: " + profiles.toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    ProfileListFragment.ProfileListListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProfileListFragment.ProfileListListener) (context);
    }

    interface ProfileListListener{
        void gotoLoginFragmentafterLogout();
        void gotoProfileFragment(Profile forum);
    }
}
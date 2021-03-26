package com.example.midtermreview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserFragment extends Fragment {
    String mState, sortBy, sortDirection;
    ListView listView;
    UsersAdapter adapter;
    ArrayList<User> usersList = new ArrayList<>();

    public void setState(String state) {
        mState = state;
    }

    public UserFragment() {
        // Required empty public constructor
    }

    public void setSort(String sortBy, String sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        getActivity().setTitle("User");

        v.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showFilter();
            }
        });

        v.findViewById(R.id.buttonSort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showSort();
            }
        });

        listView = v.findViewById(R.id.listViewuser);


        if (mState == null || mState.equals("All States")) {
            usersList.clear();
            usersList.addAll(Data.users);
        } else {
            usersList.clear();
            for (User user : Data.users) {
                if (user.state.equals(mState)) {
                    usersList.add(user);
                }
            }
        }

        if (sortBy != null && sortDirection != null) {
            Collections.sort(usersList, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    int comparison = 0;

                    if (sortBy.equals("Age")) {
                        comparison = user1.age - user2.age;
                    } else if (sortBy.equals("Name")) {
                        comparison = user1.name.compareTo(user2.name);
                    } else if (sortBy.equals("State")) {
                        comparison = user1.state.compareTo(user2.state);
                    }

                    if (sortDirection.equals("ASC")) {
                        comparison = -1 * comparison;
                    }

                    return comparison;
                }
            });
        }

        adapter = new UsersAdapter(getActivity(), R.layout.user_list_item, usersList);
        listView.setAdapter(adapter);
        return v;
    }

    UsersFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UsersFragmentListener) context;
    }

    interface UsersFragmentListener {
        void showFilter();
        void showSort();
    }
}
package com.example.homework03;

/*
    Assignment # In Class Assignment 05
    File Name App list Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppListFragment extends Fragment {
    private static final String TAG = "TAG_APPSLIST";
    String category, token;



    public AppListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(category!=null) {
            getActivity().setTitle(category);
        }
        View view =  inflater.inflate(R.layout.fragment_app_list, container, false);
        RecyclerView recyclerView_app = view.findViewById(R.id.recyclerView_app);


        DataServices.getAppsByCategory(token, category, new DataServices.DataResponse<DataServices.App>() {
            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                //app Adpter and recyclerview
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getContext(), TAG + "FAILED", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setCategory(String category, String token) {
        this.category = category;
        this.token = token;
    }

    AppListFragment.AppsListListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (AppListFragment.AppsListListener)(context);
    }

    interface AppsListListener{
        void gotoAppDetailsFragment(DataServices.App app);
    }
}
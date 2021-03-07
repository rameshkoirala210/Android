package com.example.homework04;
/*
    Assignment # In Class Assignment 05
    File Name App list Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppsListFragment extends Fragment {
    private static final String TAG = "TAG_APPSLIST";
    String category, token;
    ListView listView_apps;


    public AppsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(category!=null) {
            getActivity().setTitle(category);
        }
        View view =  inflater.inflate(R.layout.fragment_apps_list, container, false);
        listView_apps = view.findViewById(R.id.listview_apps);
        DataServices.getAppsByCategory(token, category, new DataServices.DataResponse<DataServices.App>() {
            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                AppAdapter adapter = new AppAdapter(getContext(), R.layout.app_item, data);
                listView_apps.setAdapter(adapter);
                listView_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mListner.gotoAppDetailsFragment(data.get(position));
                    }
                });
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

    AppsListFragment.AppsListListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (AppsListFragment.AppsListListener)(context);
    }

    interface AppsListListener{
        void gotoAppDetailsFragment(DataServices.App app);
    }
}
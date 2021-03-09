package com.example.inclass05;
/*
    Assignment # In Class Assignment 05
    File Name App Category Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppCategoriesFragment extends Fragment {
    private static final String TAG = "TAG_AppCategory";
    TextView textView_welcome;
    ListView listView_appCategory;
    String token;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("App Categories");
        View view =  inflater.inflate(R.layout.fragment_app_categories, container, false);
        textView_welcome = view.findViewById(R.id.textViewWelcome);
        listView_appCategory = view.findViewById(R.id.listview_category);

        if (token != null){
            DataServices.getAccount(token, new DataServices.AccountResponse() {
                @Override
                public void onSuccess(DataServices.Account account) {
                    textView_welcome.setText("Welcome " + account.getName());
                }
                @Override
                public void onFailure(DataServices.RequestException exception) {
                    Toast.makeText(getContext(), "Unable to get account", Toast.LENGTH_SHORT).show();
                }
            });
            DataServices.getAppCategories(token, new DataServices.DataResponse<String>() {
                @Override
                public void onSuccess(ArrayList<String> data) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
                    listView_appCategory.setAdapter(adapter);
                    Log.d(TAG, "onSuccess: 1");
                    listView_appCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String x = data.get(position);
                            //Log.d(TAG, "onItemClick: " + x);
                            mListner.gotoAppListFragment(data.get(position));
                        }
                    });
                }
                @Override
                public void onFailure(DataServices.RequestException exception) {
                    Toast.makeText(getContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        view.findViewById(R.id.buttonLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.logout();
            }
        });
        return view;
    }

    public void sendToken(String token) {
        this.token = token;
    }

    AppCategoriesFragment.AppCategoryListener mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListner = (AppCategoriesFragment.AppCategoryListener)(context);
    }

    interface AppCategoryListener{
        void gotoAppListFragment(String category);
        void logout();
    }
}
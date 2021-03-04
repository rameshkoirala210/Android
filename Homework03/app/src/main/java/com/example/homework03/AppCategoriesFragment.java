package com.example.homework03;

/*
    Assignment # Homework #03
    File Name App Category Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppCategoriesFragment extends Fragment {
    private static final String TAG = "TAG_AppCategory";
    TextView textView_welcome;
    RecyclerView recyclerView_appCategory;
    String token;
    LinearLayoutManager layoutManager;
    CategoryRecyclerAdapter adapter;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("App Categories");
        View view =  inflater.inflate(R.layout.fragment_app_categories, container, false);
        textView_welcome = view.findViewById(R.id.textViewWelcome);
        recyclerView_appCategory = view.findViewById(R.id.recyclerView_appCategory);
        recyclerView_appCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_appCategory.setLayoutManager(layoutManager);


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
                  //recyclerview suff

                   adapter = new CategoryRecyclerAdapter(data, mListner);
                   recyclerView_appCategory.setAdapter(adapter);
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
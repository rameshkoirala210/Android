package com.example.hw04;
/*
    Assignment # Homework 04
    File Name App Category Fragment
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
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
            new doAsyncTaskgetAccount().execute(token);
            new doAsyncTaskAppCaregories().execute();
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


    class doAsyncTaskAppCaregories extends AsyncTask<ListView, Integer, ArrayList<String>> {
        ArrayList<String> data = new ArrayList<>();
        ArrayAdapter<String> adapter;

        @Override
        protected ArrayList<String> doInBackground(ListView... listViews) {
            try {
                data = DataServices.getAppCategories(token);
                Log.d(TAG, "doInBackground: " + data);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "doInBackground2: " + data);
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
            listView_appCategory.setAdapter(adapter);

            if (strings == null){
                Toast.makeText(getContext(), "Unable to retrieve data", Toast.LENGTH_SHORT).show();
            }else {
                listView_appCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mListner.gotoAppListFragment(strings.get(position));
                    }
                });
            }
        }


    }
    class doAsyncTaskgetAccount extends AsyncTask<String, Integer, DataServices.Account>{

        @Override
        protected DataServices.Account doInBackground(String... strings) {
            String token = strings[0];
            DataServices.Account data = null;
            try {
                data = DataServices.getAccount(token);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(DataServices.Account account) {
            if (account == null){
                Toast.makeText(getContext(), "Unable to get account", Toast.LENGTH_SHORT).show();
            }else{
                textView_welcome.setText("Welcome " + account.getName());
            }
        }
    }
}
package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.inclass07.ContactDetailsFragment.ContactDetailsListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.*;

public class ContactsListFragment extends Fragment {
    private static final String TAG = "TAGContactList";
    RecyclerView recyclerViewContacts;
    ArrayList<Contact> contacts = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ContactListAdapter adapter;

    private final OkHttpClient client = new OkHttpClient();

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        recyclerViewContacts = v.findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewContacts.setLayoutManager(layoutManager);
        getcontacts();

//        adapter = new ContactListAdapter(contacts,mListener);
//        recyclerViewContacts.setAdapter(adapter);
//        recyclerViewContacts.setAdapter(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String x = String.valueOf(contacts.get(position));
//                Log.d("TAG", "onItemClick: " + x);
//            }
//        });


        return v;
    }

    void getcontacts(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.d(TAG, "onResponse: " + body);
//                    for (int i = 0; i < 10; i++){
//                        contacts.add(new Contact("ID" + i, "RAMEHS", "RAMEHS", "PHONE", "TYPE"));
//                    }
                    if (body.equals("")){

                    } else {
                        String[] lines = body.split("\n");//split the entire body into each line
                        for (String line: lines){ //extract contact data from one line
                            String[] contactData = line.split(",");
                            contacts.add(new Contact(contactData[0], contactData[1], contactData[2], contactData[3], contactData[4]));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ContactListAdapter(contacts,mListener);
                                recyclerViewContacts.setAdapter(adapter);
                            }
                        });
                    }
                }
            }
        });

    }
    ContactsListFragment.ContactListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ContactsListFragment.ContactListListener)(context);
    }

    interface ContactListListener{
        void gotoContactetailsFrangment(String category);
        void delete();
    }
}
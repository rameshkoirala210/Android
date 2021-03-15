package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactDetailsFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Contact> contacts = new ArrayList<>();
    int position;
    TextView textViewDetailsId, textViewDetailsName;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_details, container, false);
        getActivity().setTitle("Contact Details Screen");

        textViewDetailsId = v.findViewById(R.id.textViewDetailsId);
        textViewDetailsName = v.findViewById(R.id.textViewDetailsName);
        textViewDetailsId.setText(contacts.get(position).id);
        textViewDetailsName.setText(contacts.get(position).name);

        v.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoeditcontractfragment(position, contacts);
            }
        });
        v.findViewById(R.id.buttonDetailsdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacts(contacts.get(position).id);
                contacts.remove(position);
                mListener.gobacktoContractlist();
            }
        });


        return v;
    }
    void deleteContacts(String id){
        RequestBody formBody = new FormBody.Builder()
                .add("id" , id)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/delete")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody.string();
                Log.d("TAG", "onResponse: " + body);
            }
        });

    }

    public void setDetails(int position, ArrayList<Contact> contacts) {
        this.position = position;
        this.contacts = contacts;
    }

    ContactDetailsFragment.ContactDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ContactDetailsFragment.ContactDetailsListener)(context);
    }

    interface ContactDetailsListener{
        void gobacktoContractlist();
        void gotoeditcontractfragment(int position,ArrayList<Contact> contacts);
    }
}
package com.example.inclass07;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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

public class NewContactFragment extends Fragment {
    ArrayList<Contact> contacts = new ArrayList<>();
    ArrayList<String> missing = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    EditText editTextNewName,editTextNewEmail,editTextNewPhone,editTextNewType;

    public NewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_contact, container, false);
        getActivity().setTitle("New Contact Screen");

        editTextNewName = v.findViewById(R.id.editTextNewName);
        editTextNewEmail = v.findViewById(R.id.editTextNewEmail);
        editTextNewPhone = v.findViewById(R.id.editTextNewPhone);
        editTextNewType = v.findViewById(R.id.editTextNewType);

        v.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missing.clear();
                if (editTextNewEmail.getText().toString().isEmpty()) {
                    missing.add("Email");
                }
                if (editTextNewName.getText().toString().isEmpty()) {
                    missing.add("Name");
                }
                if (editTextNewPhone.getText().toString().isEmpty()) {
                    missing.add("Phone");
                }
                if (editTextNewType.getText().toString().isEmpty()) {
                    missing.add("Type");
                }
                if(missing.size() > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Missing Field Detected")
                            .setMessage("Please Enter In The Fields\n" + missing.toString())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }else{
                    //contacts.add(contact);
                    createContacts(editTextNewName.getText().toString(),
                            editTextNewEmail.getText().toString(),
                            editTextNewPhone.getText().toString(),
                            editTextNewType.getText().toString());
                    //mListener.gobacktoContractlist();
                }
            }
        });
        v.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ContactsListFragment()).commit();
            }
        });

        return v;
    }
    void createContacts(String name, String email, String phone, String type) {
        RequestBody formBody = new FormBody.Builder()
                .add("name" , name)
                .add("email" , email)
                .add("phone" , phone)
                .add("type" , type)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/create")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "onResponse: FAILED " + response.body().string());
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListener.gobacktoContractlist();
                        }
                    });
                    Log.d("TAG", "onResponse: " + body + response.isSuccessful());
                }
            }
        });
    }

    public void sentContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    ContactDetailsFragment.ContactDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ContactDetailsFragment.ContactDetailsListener)(context);
    }
}
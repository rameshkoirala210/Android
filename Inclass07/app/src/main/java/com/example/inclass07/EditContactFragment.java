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

public class EditContactFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    EditText editTextUpdateName,editTextUpdateEmail,editUpdateNumber,editTextUpdateType,editTextUpdateID;
    ArrayList<Contact> contacts = new ArrayList<>();
    int position;

    public EditContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Edit Contact Screen");

        View v = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        editTextUpdateName = v.findViewById(R.id.editTextUpdateName);
        editTextUpdateEmail = v.findViewById(R.id.editTextUpdateEmail);
        editUpdateNumber = v.findViewById(R.id.editUpdateNumber);
        editTextUpdateType = v.findViewById(R.id.editTextUpdateType);
        editTextUpdateID = v.findViewById(R.id.editTextUpdateID);

        editTextUpdateID.setText(contacts.get(position).id.toString());
        editTextUpdateName.setText(contacts.get(position).name.toString());
        editTextUpdateEmail.setText(contacts.get(position).email.toString());
        editUpdateNumber.setText(contacts.get(position).phone.toString());
        editTextUpdateType.setText(contacts.get(position).type.toString());

        Contact contact = new Contact(editTextUpdateID.getText().toString(),
                editTextUpdateName.getText().toString(),
                editTextUpdateEmail.getText().toString(),
                editUpdateNumber.getText().toString(),
                editTextUpdateType.getText().toString());

        v.findViewById(R.id.buttonedtiContractUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUpdateID.getText().toString().isEmpty() ||
                        editTextUpdateName.getText().toString().isEmpty() ||
                        editTextUpdateEmail.getText().toString().isEmpty() ||
                        editUpdateNumber.getText().toString().isEmpty() ||
                        editTextUpdateType.getText().toString().isEmpty() ){

                }else{
                    contacts.get(position).id = editTextUpdateID.getText().toString();
                    contacts.get(position).name = editTextUpdateName.getText().toString();
                    contacts.get(position).email = editTextUpdateEmail.getText().toString();
                    contacts.get(position).phone = editUpdateNumber.getText().toString();
                    contacts.get(position).type = editTextUpdateType.getText().toString();

                    updateContacts(editTextUpdateID.getText().toString(), editTextUpdateName.getText().toString(),
                            editTextUpdateEmail.getText().toString(), editUpdateNumber.getText().toString(), editTextUpdateType.getText().toString());
                    mListener.gotoContactdetailsFragment(position, contacts);
                }
            }
        });

        return v;
    }

    public void editDetails(int position, ArrayList<Contact> contacts) {
        this.position = position;
        this.contacts = contacts;
    }
    void updateContacts(String id, String name, String email, String phone, String type) {
        RequestBody formBody = new FormBody.Builder()
                .add("id" , id)
                .add("name" , name)
                .add("email" , email)
                .add("phone" , phone)
                .add("type" , type)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/update")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG", "onFailure: " + e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String body = responseBody.string();
                Log.d("TAG", "onResponse: " + response.isSuccessful());
            }
        });
    }

    ContactsListFragment.ContactListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ContactsListFragment.ContactListListener)(context);
    }

}
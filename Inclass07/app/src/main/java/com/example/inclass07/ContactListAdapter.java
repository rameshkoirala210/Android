package com.example.inclass07;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactListAdapter extends ArrayAdapter<Contact>{
    private final OkHttpClient client = new OkHttpClient();

    public ContactListAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_items, parent, false);
        }
        Contact contact = getItem(position);
        TextView id = convertView.findViewById(R.id.textViewID);
        TextView name = convertView.findViewById(R.id.textViewName);
        TextView email = convertView.findViewById(R.id.textViewEmail);
        TextView type = convertView.findViewById(R.id.textViewType);
        TextView phoneNumber = convertView.findViewById(R.id.textViewPhoneNumber);
        Button delete = convertView.findViewById(R.id.buttonDelete);

        id.setText(contact.id);
        name.setText(contact.name);
        email.setText(contact.email);
        type.setText(contact.type);
        phoneNumber.setText(contact.phone);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacts(contact.id);
            }
        });

        return convertView;
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
}

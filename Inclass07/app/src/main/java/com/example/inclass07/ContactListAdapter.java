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
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {
    ArrayList<Contact> contacts;
    private final OkHttpClient client = new OkHttpClient();
    ContactsListFragment.ContactListListener mListener;

    public ContactListAdapter(ArrayList<Contact> contacts, ContactsListFragment.ContactListListener mListener){
        this.contacts = contacts;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public ContactListAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_items, parent, false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view, ContactListAdapter.this);
        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactsViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contact = contact;
        holder.position = String.valueOf(contacts.get(position));
        holder.tvId.setText(contact.id);
        holder.tvName.setText(contact.name);
        holder.tvEmail.setText(contact.email);
        holder.tvType.setText(contact.type);
        holder.tvPhone.setText(contact.phone);
        holder.mListener = this.mListener;
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvEmail, tvPhone, tvType;
        String position;
        Button btnDelete;
        Contact contact;
        ContactsListFragment.ContactListListener mListener;

        public ContactsViewHolder(@NonNull View itemView, ContactListAdapter adapter) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewID);
            tvName = itemView.findViewById(R.id.textViewName);
            tvEmail = itemView.findViewById(R.id.textViewEmail);
            tvType = itemView.findViewById(R.id.textViewType);
            tvPhone = itemView.findViewById(R.id.textViewPhoneNumber);
            btnDelete = itemView.findViewById(R.id.buttonDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: Button has been clicked");
                    deleteContacts(position);
                    adapter.notifyDataSetChanged();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.gotoContactetailsFrangment(position);
                }
            });
        }
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


package com.example.midtermreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        }

        User user = getItem(position);
        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewState = convertView.findViewById(R.id.textViewState);
        TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);
        TextView textViewAge = convertView.findViewById(R.id.textViewAge);


        if(user.gender.equals("Female")){
            imageViewIcon.setImageResource(R.drawable.avatar_female);
        } else{
            imageViewIcon.setImageResource(R.drawable.avatar_male);
        }

        textViewName.setText(user.name);
        textViewState.setText(user.state);
        textViewGroup.setText(user.group);
        textViewAge.setText(user.age + " Years Old");

        return convertView;
    }
}

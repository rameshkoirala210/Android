package com.example.homework03;
/*
    Assignment # Homework #03
    File Name App Details Recycler Adapter
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppDetailsRecyclerAdapter extends RecyclerView.Adapter<AppDetailsRecyclerAdapter.AppDetailsViewHolder>{
    ArrayList<String> details;

    public AppDetailsRecyclerAdapter(ArrayList<String> details) {
        this.details = details;
    }
    @NonNull
    @Override
    public AppDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        AppDetailsViewHolder appDetailsViewHolder = new AppDetailsViewHolder(view);
        return appDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppDetailsViewHolder holder, int position) {
        holder.textViewcategory.setText(details.get(position));
    }

    @Override
    public int getItemCount() {
        return this.details.size();
    }

    public static class AppDetailsViewHolder extends RecyclerView.ViewHolder{
        TextView textViewcategory;
        public AppDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewcategory = itemView.findViewById(android.R.id.text1);
        }
    }
}

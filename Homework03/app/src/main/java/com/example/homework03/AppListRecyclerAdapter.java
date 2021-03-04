package com.example.homework03;
/*
    Assignment # Homework #03
    File Name AppList Recycler Adapter
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppListRecyclerAdapter extends RecyclerView.Adapter<AppListRecyclerAdapter.AppListViewHolder> {
    ArrayList<DataServices.App> apps;
    AppListFragment.AppsListListener mListner;
    public AppListRecyclerAdapter(ArrayList<DataServices.App> apps, AppListFragment.AppsListListener mListner) {
        this.apps = apps;
        this.mListner = mListner;
    }

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appitem, parent, false);
        AppListViewHolder appListViewHolder = new AppListViewHolder(view);
        return appListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppListViewHolder holder, int position) {
        DataServices.App app = apps.get(position);
        holder.app = app;
        holder.textViewAppName.setText(app.name);
        holder.textViewDeveloper.setText(app.artistName);
        holder.textViewReleaseDate.setText(app.releaseDate);
        holder.mListner = this.mListner;
    }

    @Override
    public int getItemCount() {
        return this.apps.size();
    }

    public static class AppListViewHolder extends RecyclerView.ViewHolder{
        TextView textViewAppName, textViewDeveloper, textViewReleaseDate;
        DataServices.App app;
        AppListFragment.AppsListListener mListner;

        public AppListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAppName = itemView.findViewById(R.id.textViewAppName);
            textViewDeveloper = itemView.findViewById(R.id.textViewAppItemDev);
            textViewReleaseDate = itemView.findViewById(R.id.textViewAppItemRelease);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListner.gotoAppDetailsFragment(app);
                }
            });
        }
    }
}

package com.example.homework03;
/*
    Assignment # Homework #03
    File Name Category Recycler Adapter
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {
    ArrayList<String> categories;
    AppCategoriesFragment.AppCategoryListener mListner;
    public CategoryRecyclerAdapter(ArrayList<String> categories, AppCategoriesFragment.AppCategoryListener mListner) {
        this.categories = categories;
        this.mListner = mListner;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.textViewcategory.setText(categories.get(position));
        holder.category = categories.get(position);
        holder.mListner = this.mListner;
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView textViewcategory;
        String category;
        AppCategoriesFragment.AppCategoryListener mListner;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewcategory = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: " + category);
                    mListner.gotoAppListFragment(category);
                }
            });
        }
    }
}

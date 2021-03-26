package com.example.midtermreview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SortFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ExternalSortAdapter adapter;
    ArrayList<String> labels;
    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sort, container, false);
        getActivity().setTitle("Sort");

        labels = new ArrayList<>();
        labels.add("Age");
        labels.add("Name");
        labels.add("State");

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ExternalSortAdapter(labels, getContext(), mListener);
        //adapter = new ExternalSortAdapter(getActivity(), (ExternalSortAdapter.ExternalSortAdapterListener) this, labels);

        recyclerView.setAdapter(adapter);



        return v;
    }


    SortFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SortFragmentListener) context;
    }

    interface SortFragmentListener extends ExternalSortAdapter.ExternalSortAdapterListener {
        @Override
        void itemSortSelected(String sortBy, String sortDirection);
    }
}
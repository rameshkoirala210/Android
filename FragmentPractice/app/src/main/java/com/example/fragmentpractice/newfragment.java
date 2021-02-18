package com.example.fragmentpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class newfragment extends Fragment {
    ImageView female1,female2,female3,male1,male2,male3;

    public newfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newfragment, container, false);
        // Inflate the layout for this fragment
        female1 = (ImageView) v.findViewById(R.id.female1);
        female2 = (ImageView) v.findViewById(R.id.female2);
        female3 = (ImageView) v.findViewById(R.id.female3);
        male1 = (ImageView) v.findViewById(R.id.male1);
        male2 = (ImageView) v.findViewById(R.id.male2);
        male3 = (ImageView) v.findViewById(R.id.male3);

        female1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("female1", female1.toString());

                Evaluation fragment = new Evaluation();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.layout,fragment).commit();
            }
        });


        return v;
    }
}
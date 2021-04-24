package com.example.passwordmanager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ContactFragment extends Fragment {
    private FirebaseAuth mAuth;
    private final String TAG = "Forumstag";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ContactAdapter adapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        getActivity().setTitle("Contact");

        mAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mListener.gotoLoginFragmentafterLogout();
            }
        });

        view.findViewById(R.id.buttonNewForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoNewForumFragment();
            }
        });


        return view;
    }


    ContactFragment.ContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ContactFragment.ContactListener) (context);
    }

    interface ContactListener{
        void gotoNewForumFragment();
        void gotoLoginFragmentafterLogout();
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{
        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ContactHolder extends RecyclerView.ViewHolder {

            public ContactHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
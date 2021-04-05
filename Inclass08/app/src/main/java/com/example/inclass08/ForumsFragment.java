package com.example.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.inclass08.R.id.textViewTitle;

public class ForumsFragment extends Fragment {
    private FirebaseAuth mAuth;
    String name;
    private final String TAG = "Forumstag" ;

    RecyclerView recyclerView;
    FourmAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Forum> fourmsList = new ArrayList<>();
    public ForumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums, container, false);

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
                mListener.gotoNewForumFragment(name);
            }
        });

        recyclerView = view.findViewById(R.id.container);
        adapter = new FourmAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        getFourms();
        return view;
    }
    private void getFourms(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("datasetforForums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){

                    for(QueryDocumentSnapshot documentSnapshot : value){
                        //Log.d(TAG, "onEvent: "+ documentSnapshot.getId() + "->" + documentSnapshot.getData());
                        Forum forum = documentSnapshot.toObject(Forum.class);
                        Log.d(TAG, "onEvent: " + forum.toString());
                        Toast.makeText(getActivity(), ""+forum.toString(), Toast.LENGTH_SHORT).show();
                        fourmsList.add(forum);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    error.printStackTrace();
                    Log.d(TAG, "onEvent: " + error);
                }
            }
        });
    }
    ForumsFragment.FourmsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsFragment.FourmsListener) (context);
    }

    public void setname(String name) {
        this.name = name;
    }

    interface FourmsListener{
        void gotoNewForumFragment(String name);
        void gotoLoginFragmentafterLogout();
    }

    private class FourmAdapter extends RecyclerView.Adapter<FourmAdapter.FourmHolder> {
        ArrayList<Forum> forums = fourmsList;

        @NonNull
        @Override
        public FourmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forum_item, parent, false);
            return new FourmHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FourmHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class FourmHolder extends RecyclerView.ViewHolder {
            ArrayList<Forum> forums = fourmsList;
            TextView textViewTitle,textViewAuthor,textViewDescription,textViewDateTime;
            ImageView imageViewtrash;
            public FourmHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
                imageViewtrash = itemView.findViewById(R.id.imageViewtrash);
            }
        }
    }
}
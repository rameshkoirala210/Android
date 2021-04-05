package com.example.hw06;

import android.content.Context;
import android.os.AsyncTask;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForumListFragment extends Fragment {
    private FirebaseAuth mAuth;
    String name;
    private final String TAG = "Forumstag" ;

    RecyclerView recyclerView;
    FourmAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Forum> fourmsList = new ArrayList<>();
    public ForumListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums_list, container, false);

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
        //recyclerView.setAdapter(adapter);

        getFourms();
        return view;
    }
    private void getFourms(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("datasetforForums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    fourmsList.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: value) {
                        Forum forum = queryDocumentSnapshot.toObject(Forum.class);
                        fourmsList.add(forum);
                    }
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }


    ForumListFragment.FourmsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumListFragment.FourmsListener) (context);
    }

    public void setname(String name) {
        this.name = name;
    }

    interface FourmsListener{
        void gotoNewForumFragment(String name);
        void gotoLoginFragmentafterLogout();
    }

    private class FourmAdapter extends RecyclerView.Adapter<FourmAdapter.FourmHolder> {
        @NonNull
        @Override
        public FourmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forum_list_item, parent, false);
            return new FourmHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FourmHolder holder, int position) {
            Forum forum = fourmsList.get(position);
            holder.setupForumItem(forum);
        }

        @Override
        public int getItemCount() {
            return fourmsList.size();
        }

        public class FourmHolder extends RecyclerView.ViewHolder {
            TextView textViewTitle,textViewAuthor,textViewDescription,textViewDateTime,textViewLike;
            ImageView imageViewtrash,imageViewLike;
            Forum forum;
            public FourmHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
                imageViewtrash = itemView.findViewById(R.id.imageViewtrash);
                textViewLike = itemView.findViewById(R.id.textViewLike);
                imageViewLike = itemView.findViewById(R.id.imageViewLike);

            }

            public void setupForumItem(Forum forum) {
                this.forum = forum;
                textViewTitle.setText(forum.getTitle());

                String desc200 = forum.getDescription().substring(0, Math.min(200, forum.getDescription().length()));

                textViewDescription.setText(desc200);
                textViewAuthor.setText(forum.getCreatedbyName());

                //TODO create like count in the database and use that to show numLikes to the users
//                int likeCount = forum..size();
//                String likeString = "No Likes";
//                if(likeCount == 1){
//                    likeString = "1 Like";
//                } else {
//                    likeString = likeCount + " Likes";
//                }

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:m a");
                textViewDateTime.setText(formatter.format(forum.datetime));

                //@TODO create UUID for forum creator and compare them here
                if(forum.getCreatedbyName() == mAuth.getCurrentUser().getDisplayName()){
                    imageViewtrash.setVisibility(View.VISIBLE);
                    imageViewtrash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //new DeleteForumTask(mForum.getForumId(), mAuthResponse.getToken()).execute();
                            //TODO create delete asynctask to delete the forum from the firebase database

                        }
                    });
                } else {
                    imageViewtrash.setVisibility(View.INVISIBLE);
                }

                //TODO first appeareance. Check whether the user like it or not
//                if(forum.getLikedBy().contains(mAuthResponse.getAccount())){
//                    imageViewLike.setImageResource(R.drawable.like_favorite);
//                } else {
//                    imageViewLike.setImageResource(R.drawable.like_not_favorite);
//                }

                imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO change the like to dislike and vise versa
                    }
                });

            }
        }
    }
}
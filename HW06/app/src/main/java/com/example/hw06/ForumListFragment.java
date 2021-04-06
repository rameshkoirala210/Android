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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ForumListFragment extends Fragment {
    private FirebaseAuth mAuth;
    private final String TAG = "Forumstag";
    private final String forumName = "forums";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        getActivity().setTitle("Forums");
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
                mListener.gotoNewForumFragment();
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
        db.collection(forumName).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    fourmsList.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: value) {
                        Forum forum = queryDocumentSnapshot.toObject(Forum.class);
                        fourmsList.add(forum);
                    }
                    //Log.d(TAG, "onEvent: " + fourmsList.toString());
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

    interface FourmsListener{
        void gotoNewForumFragment();
        void gotoLoginFragmentafterLogout();
        void gotoForumFragment(Forum forum);
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
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoForumFragment(forum);
                    }
                });
            }

            public void setupForumItem(Forum forum) {
                this.forum = forum;
                textViewTitle.setText(forum.getTitle());
                String desc200 = forum.getDescription().substring(0, Math.min(200, forum.getDescription().length()));
                textViewDescription.setText(desc200);
                textViewAuthor.setText(forum.getCreatedbyName());
                textViewLike.setText(forum.getLikedBy().size() + " Likes");
                textViewDateTime.setText(forum.datetime.toDate().toString());


                if(forum.getUUID().equals(mAuth.getCurrentUser().getUid())){
                    imageViewtrash.setVisibility(View.VISIBLE);
                    imageViewtrash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection(forumName).document(forum.getDocumentID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + forum + " successfully deleted");
                                }
                            });
                        }
                    });
                } else {
                    imageViewtrash.setVisibility(View.INVISIBLE);
                }

                if(forum.getLikedBy().contains(mAuth.getCurrentUser().getUid())){
                    imageViewLike.setImageResource(R.drawable.like_favorite);
                } else {
                    imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }

                imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO push changes to firebase
                        if(forum.getLikedBy().contains(mAuth.getCurrentUser().getUid())){
                            forum.getLikedBy().remove(mAuth.getCurrentUser().getUid());
                        } else {
                            forum.getLikedBy().add(mAuth.getCurrentUser().getUid());
                        }
                        changeFirebaseData(forum);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }
    }

    private void changeFirebaseData(Forum forum) {
        HashMap<String, Object> likedByList = new HashMap<>();
        likedByList.put("likedBy", forum.getLikedBy());
        db.collection(forumName).document(forum.getDocumentID())
                .update(likedByList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, "LIKED BY SUCCESS");
            }
        });
    }
}
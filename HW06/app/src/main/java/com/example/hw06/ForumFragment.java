package com.example.hw06;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.$Gson$Preconditions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ForumFragment extends Fragment {
    private static final String TAG = "TAG_Forum";
    public ForumFragment() {
    }
    TextView textViewForumTitle, textViewForumOwnerName, textViewForumDesc, textViewNumComments;
    EditText editTextTextComment;
    RecyclerView recyclerView;
    CommentsAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Comment> comments = new ArrayList<>();
    Forum forum;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    public void sendForum(Forum forum) {
        this.forum = forum;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Forum Details");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        textViewForumTitle = view.findViewById(R.id.textViewForumTitle);
        textViewForumOwnerName = view.findViewById(R.id.textViewForumOwnerName);
        textViewForumDesc = view.findViewById(R.id.textViewForumDesc);
        textViewNumComments = view.findViewById(R.id.textViewNumComments);

        //Populate the textviews
        textViewForumTitle.setText(forum.getTitle());
        textViewForumOwnerName.setText(forum.getCreatedbyName());
        textViewForumDesc.setText(forum.getDescription());

        editTextTextComment = view.findViewById(R.id.editTextTextComment);
        recyclerView = view.findViewById(R.id.recyclerView);

        view.findViewById(R.id.buttonPostSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = editTextTextComment.getText().toString();

                if(commentText.isEmpty()){
                    Toast.makeText(getActivity(), "Enter comment text!", Toast.LENGTH_SHORT).show();
                } else {
                    postFirebaseComment(commentText);
                }
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CommentsAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        getFirebaseComments();
        return view;
    }

    class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder>{
        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_list_item, parent, false);
            return new CommentHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
            //Log.d(TAG, "onBindViewHolder: " + comments.toString());
            Comment comment = comments.get(position);
            holder.setupComment(comment);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        class CommentHolder extends RecyclerView.ViewHolder{
            TextView textViewDesc, textViewOwner, textViewDate;
            ImageView imageViewDeleteComment;

            public CommentHolder(@NonNull View itemView) {
                super(itemView);
                textViewDesc = itemView.findViewById(R.id.textViewDescription);
                textViewOwner = itemView.findViewById(R.id.textViewAuthor);
                textViewDate = itemView.findViewById(R.id.textViewDate);
                imageViewDeleteComment = itemView.findViewById(R.id.imageViewDeleteComment);
            }

            public void setupComment(Comment comment){
                textViewDesc.setText(comment.getDescription());
                textViewOwner.setText(comment.getCreatedByName());
                textViewDate.setText(comment.getDatetime().toDate().toString());

                if(comment.getCreatedByUUID().equals(mAuth.getCurrentUser().getUid())){
                    Log.d(TAG,  comment.toString());
                    imageViewDeleteComment.setVisibility(View.VISIBLE);
                    imageViewDeleteComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("Comments").document(comment.getCommentID())
                                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Comment Successfully Deleted");
                                }
                            });
                        }
                    });
                } else {
                    Log.d(TAG, "setupComment: ALWAYS ELSE");
                    imageViewDeleteComment.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    private void getFirebaseComments() {
        db.collection("Comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    comments.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot: value) {
                        Comment comment = queryDocumentSnapshot.toObject(Comment.class);
                        if (comment.getForumID().equals(forum.getDocumentID())) {
                            comments.add(comment);
                        }
                    }
                }
                textViewNumComments.setText(comments.size() + " Comments");
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void postFirebaseComment(String str) {
        HashMap<String,Object> comment = new HashMap<>();
        comment.put("createdByName", mAuth.getCurrentUser().getDisplayName());
        comment.put("createdByUUID", mAuth.getCurrentUser().getUid());
        comment.put("datetime", Timestamp.now());
        comment.put("description", str);
        comment.put("forumID",forum.getDocumentID());

        db.collection("Comments").add(comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        comment.put("commentID", documentReference.getId());
                        documentReference.update(comment);
                        editTextTextComment.setText("");
                    }
                });
    }


}
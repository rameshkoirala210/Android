package com.example.hw07;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentsFragment extends Fragment {
    private static final String TAG = "TAG_Comments";

    private Image image;
    private TextView textViewNumLikes;
    private ImageView imageView;
    private EditText editTextComment;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    RecyclerView recyclerView;
    CommentsAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Comment> comments = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_comments, container, false);
        textViewNumLikes = view.findViewById(R.id.textViewNumComments);
        imageView = view.findViewById(R.id.imageViewComment);
        editTextComment = view.findViewById(R.id.editTextTextComment);
        recyclerView = view.findViewById(R.id.recyclerView);
        textViewNumLikes.setText(image.getComments().size() + " Comments");
        setImageOnImageView();

        view.findViewById(R.id.buttonPostSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = editTextComment.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if(commentText.isEmpty()) {
                    builder.setTitle("Missing Fields")
                            .setMessage("Comment is Empty")
                            .setPositiveButton("OK", null)
                            .show();
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

    private void setImageOnImageView() {
        StorageReference imgRef = storageReference.child(image.getStorageRef());
        imgRef.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    public void setImage(Image image) {
        this.image = image;
    }

    class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder>{
        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item, parent, false);
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
                        if (comment.getimageID().equals(image.getDocumentID())){
                            comments.add(comment);
                        }
                    }
                }
                textViewNumLikes.setText(comments.size() + " Comments");
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
        comment.put("imageID", image.getDocumentID());

        db.collection("Comments").add(comment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    comment.put("commentID", task.getResult().getId());
                    task.getResult().update(comment);
                    editTextComment.setText("");
                    Log.d(TAG, "onComplete: Comment updated successful");
                } else {
                    Log.d(TAG, "onComplete: FAILED");
                }
            }
        });
    }
}
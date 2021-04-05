package com.example.hw06;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForumFragment extends Fragment {
    private static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";
    private static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";

    private DataServices.Forum mForum;
    private DataServices.AuthResponse mAuthResponse;

    public ForumFragment() {
    }

    public static ForumFragment newInstance(DataServices.Forum forum, DataServices.AuthResponse authResponse) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FORUM, forum);
        args.putSerializable(ARG_PARAM_AUTH, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = (DataServices.Forum) getArguments().getSerializable(ARG_PARAM_FORUM);
            mAuthResponse = (DataServices.AuthResponse) getArguments().getSerializable(ARG_PARAM_AUTH);
        }
    }

    TextView textViewForumTitle, textViewForumOwnerName, textViewForumDesc, textViewNumComments;
    EditText editTextTextComment;
    RecyclerView recyclerView;
    CommentsAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<DataServices.Comment> comments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Forum Details");

        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        textViewForumTitle = view.findViewById(R.id.textViewForumTitle);
        textViewForumOwnerName = view.findViewById(R.id.textViewForumOwnerName);
        textViewForumDesc = view.findViewById(R.id.textViewForumDesc);
        textViewNumComments = view.findViewById(R.id.textViewNumComments);

        editTextTextComment = view.findViewById(R.id.editTextTextComment);
        recyclerView = view.findViewById(R.id.recyclerView);

        textViewForumTitle.setText(mForum.getTitle());
        textViewForumOwnerName.setText(mForum.getCreatedBy().getName());
        textViewForumDesc.setText(mForum.getDescription());
        textViewNumComments.setText("");


        view.findViewById(R.id.buttonPostSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = editTextTextComment.getText().toString();

                if(commentText.isEmpty()){
                    Toast.makeText(getActivity(), "Enter comment text!", Toast.LENGTH_SHORT).show();
                } else {
                    new CreateNewCommentTask(mAuthResponse.getToken(), mForum.getForumId(), commentText).execute();
                }
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new CommentsAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        new GetCommentsTask(mAuthResponse.getToken(), mForum.getForumId()).execute();


        return view;
    }


    class GetCommentsTask extends AsyncTask<Void, String, ArrayList<DataServices.Comment>> {

        String mToken;
        long mForumId;

        public GetCommentsTask(String token, long forumId){
            this.mForumId = forumId;
            this.mToken = token;
        }

        @Override
        protected ArrayList<DataServices.Comment> doInBackground(Void... voids) {
            try {
                return DataServices.getForumComments(mToken, mForumId);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.Comment> receivedCommentsList) {
            super.onPostExecute(receivedCommentsList);
            if(receivedCommentsList != null){
                comments.clear();
                comments.addAll(receivedCommentsList);
                adapter.notifyDataSetChanged();

                textViewNumComments.setText(comments.size() + " Comments");


            }
        }
    }

    class CreateNewCommentTask extends AsyncTask<Void, String, DataServices.Comment>{
        String mToken;
        long mForumId;
        String mCommentText;

        public CreateNewCommentTask(String token, long forumId, String commentText){
            this.mForumId = forumId;
            this.mToken = token;
            this.mCommentText = commentText;
        }

        @Override
        protected DataServices.Comment doInBackground(Void... voids) {

            try {
                return DataServices.createComment(mToken, mForumId, mCommentText);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.Comment comment) {
            super.onPostExecute(comment);
            new GetCommentsTask(mToken, mForumId).execute();
        }
    }

    class DeleteCommentTask extends AsyncTask<Void, String, Boolean>{
        String mToken;
        long mForumId, mCommentId;

        public DeleteCommentTask(String token, long forumId, long commentId){
            this.mForumId = forumId;
            this.mToken = token;
            this.mCommentId = commentId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                DataServices.deleteComment(mToken, mForumId, mCommentId);
                return true;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            super.onPostExecute(isSuccessful);
            if(isSuccessful){
                new GetCommentsTask(mToken, mForumId).execute();
            }
        }
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
            DataServices.Comment comment = comments.get(position);
            holder.setupComment(comment);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        class CommentHolder extends RecyclerView.ViewHolder{
            TextView textViewDesc, textViewOwner, textViewDate;
            ImageView imageViewDeleteComment;
            long commentId;

            public CommentHolder(@NonNull View itemView) {
                super(itemView);
                textViewDesc = itemView.findViewById(R.id.textViewDesc);
                textViewOwner = itemView.findViewById(R.id.textViewOwner);
                textViewDate = itemView.findViewById(R.id.textViewDate);
                imageViewDeleteComment = itemView.findViewById(R.id.imageViewDeleteComment);
            }

            public void setupComment(DataServices.Comment comment){
                textViewDesc.setText(comment.getText());
                textViewOwner.setText(comment.getCreatedBy().getName());

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:m a");
                textViewDate.setText(formatter.format(comment.getCreatedAt()));

                commentId = comment.getCommentId();

                if(comment.getCreatedBy().uid == mAuthResponse.getAccount().uid){
                    imageViewDeleteComment.setVisibility(View.VISIBLE);
                    imageViewDeleteComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DeleteCommentTask(mAuthResponse.getToken(), mForum.getForumId(), commentId).execute();
                        }
                    });
                } else {
                    imageViewDeleteComment.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

}
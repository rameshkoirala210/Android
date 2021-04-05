package com.example.hw06;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FourmListFragment extends Fragment {

    private static final String ARG_PARAM_AUTH_RES = "ARG_PARAM_AUTH_RES";

    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse mAuthResponse;

    public FourmListFragment() {
        // Required empty public constructor
    }

    public static FourmListFragment newInstance(DataServices.AuthResponse authResponse) {
        FourmListFragment fragment = new FourmListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_AUTH_RES, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuthResponse = (DataServices.AuthResponse) getArguments().getSerializable(ARG_PARAM_AUTH_RES);
        }
    }

    RecyclerView recyclerView;
    ForumsAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<DataServices.Forum> forums = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Forums List");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums_list, container, false);

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logOut();
            }
        });

        view.findViewById(R.id.buttonAddNewForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoAddNewForum();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ForumsAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        (new GetForumsTask()).execute(mAuthResponse.getToken());

        return view;
    }

    class GetForumsTask extends AsyncTask<String, String, ArrayList<DataServices.Forum>> {

        @Override
        protected ArrayList<DataServices.Forum> doInBackground(String... strings) {
            String token = strings[0];
            try {
                return DataServices.getAllForums(token);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                publishProgress(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.Forum> receivedForumsList) {
            super.onPostExecute(receivedForumsList);
            if(receivedForumsList != null){
                forums.clear();
                forums.addAll(receivedForumsList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    class DeleteForumTask extends AsyncTask<Void, String, Boolean>{
        long mForumId;
        String mToken;

        public DeleteForumTask(long forumId, String token){
            this.mForumId = forumId;
            this.mToken = token;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                DataServices.deleteForum(mToken, mForumId);
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
                //get the new list of forums ..
                new GetForumsTask().execute(mToken);
            }
        }
    }

    class LikeOrUnlikeForumTask extends AsyncTask<Void, Void, Boolean>{
        long mForumId;
        String mToken;
        boolean mLike;

        public LikeOrUnlikeForumTask(long forumId, String token, boolean like){
            this.mForumId = forumId;
            this.mToken = token;
            this.mLike = like;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(mLike){
                try {
                    DataServices.likeForum(mToken, mForumId);
                    return true;
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    DataServices.unLikeForum(mToken, mForumId);
                    return true;
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            super.onPostExecute(isSuccessful);

            if(isSuccessful){
                //get the new list of forums ..
                new GetForumsTask().execute(mToken);
            }
        }
    }

    class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumViewHolder> {

        @NonNull
        @Override
        public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forum_list_item, parent, false);
            return new ForumViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return forums.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
            DataServices.Forum forum = forums.get(position);
            holder.setupForumItem(forum);
        }

        class ForumViewHolder extends RecyclerView.ViewHolder{
            TextView textViewTitle, textViewDesc, textViewOwner, textViewLikesAndDate;
            ImageView imageViewLike, imageViewDeleteForum;
            DataServices.Forum mForum;


            public ForumViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                textViewDesc = itemView.findViewById(R.id.textViewDesc);
                textViewOwner = itemView.findViewById(R.id.textViewOwner);
                textViewLikesAndDate = itemView.findViewById(R.id.textViewLikesAndDate);
                imageViewLike = itemView.findViewById(R.id.imageViewLike);
                imageViewDeleteForum = itemView.findViewById(R.id.imageViewDeleteForum);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoForumDetails(mForum);
                    }
                });
            }

            public void setupForumItem(DataServices.Forum forum){
                this.mForum = forum;
                textViewTitle.setText(forum.getTitle());

                String desc200 = forum.getDescription().substring(0, Math.min(200, forum.getDescription().length()));

                textViewDesc.setText(desc200);
                textViewOwner.setText(forum.getCreatedBy().getName());

                int likeCount = forum.getLikedBy().size();
                String likeString = "No Likes";
                if(likeCount == 1){
                    likeString = "1 Like";
                } else {
                    likeString = likeCount + " Likes";
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:m a");
                textViewLikesAndDate.setText(likeString + " | " + formatter.format(forum.getCreatedAt()));

                if(forum.getCreatedBy().uid == mAuthResponse.getAccount().uid){
                    imageViewDeleteForum.setVisibility(View.VISIBLE);
                    imageViewDeleteForum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DeleteForumTask(mForum.getForumId(), mAuthResponse.getToken()).execute();
                        }
                    });
                } else {
                    imageViewDeleteForum.setVisibility(View.INVISIBLE);
                }

                if(forum.getLikedBy().contains(mAuthResponse.getAccount())){
                    imageViewLike.setImageResource(R.drawable.like_favorite);
                } else {
                    imageViewLike.setImageResource(R.drawable.like_not_favorite);
                }

                imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(forum.getLikedBy().contains(mAuthResponse.getAccount())){
                            new LikeOrUnlikeForumTask(mForum.getForumId(), mAuthResponse.getToken(), false).execute();
                        } else {
                            new LikeOrUnlikeForumTask(mForum.getForumId(), mAuthResponse.getToken(), true).execute();
                        }
                    }
                });




            }
        }
    }

    ForumsListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ForumsListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ForumsListListener");
        }
    }

    interface ForumsListListener{
        void gotoForumDetails(DataServices.Forum forum);
        void logOut();
        void gotoAddNewForum();
    }
}
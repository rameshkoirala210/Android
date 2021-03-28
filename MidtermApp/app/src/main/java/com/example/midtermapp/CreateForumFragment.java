package com.example.midtermapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class CreateForumFragment extends Fragment {
    private static final String ARG_PARAM_TOKEN = "ARG_PARAM_TOKEN";
    private String mToken;

    public CreateForumFragment() {
        // Required empty public constructor
    }

    public static CreateForumFragment newInstance(String token) {
        CreateForumFragment fragment = new CreateForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_PARAM_TOKEN);
        }
    }

    EditText editTextForumDesc, editTextForumTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Create Forum");
        View view = inflater.inflate(R.layout.fragment_create_forum, container, false);

        editTextForumDesc = view.findViewById(R.id.editTextForumDesc);
        editTextForumTitle = view.findViewById(R.id.editTextForumTitle);

        view.findViewById(R.id.buttonCreateForumSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextForumTitle.getText().toString();
                String desc = editTextForumDesc.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Title !!", Toast.LENGTH_SHORT).show();
                } else if(desc.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Desc !!", Toast.LENGTH_SHORT).show();
                } else {
                    new CreateForumTask().execute(mToken, title, desc);
                }
            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.doneCreateForum();
            }
        });

        return view;
    }

    class CreateForumTask extends AsyncTask<String, String, DataServices.Forum>{

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected DataServices.Forum doInBackground(String... strings) {
            String token = strings[0];
            String title = strings[1];
            String desc = strings[2];

            try {
                return  DataServices.createForum(token, title, desc);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                publishProgress(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.Forum forum) {
            super.onPostExecute(forum);
            if(forum != null){
                mListener.doneCreateForum();
            }
        }
    }


    CreateForumListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateForumListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreateForumListener");
        }
    }

    interface CreateForumListener{
        void doneCreateForum();
    }

}
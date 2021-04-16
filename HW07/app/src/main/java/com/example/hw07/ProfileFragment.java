package com.example.hw07;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.annotation.GlideType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ProfileFragment extends Fragment {
    private static final String TAG = "TAG_Profile";

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    public Uri imageUri;

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ProfileAdapter adapter;
    Profile profile;
    Button buttonNewPost;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile Fragment");
        mAuth = FirebaseAuth.getInstance();
        buttonNewPost = view.findViewById(R.id.buttonNewPost);
        buttonNewPost.setVisibility(View.INVISIBLE);

        if (mAuth.getCurrentUser().getUid().equals(profile.getUUID())) {
            buttonNewPost.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.buttonNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add new picture and update UI
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
            }
        });

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mListener.gotoLoginFragmentafterLogout();
            }
        });

        recyclerView = view.findViewById(R.id.container);
        adapter = new ProfileAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData()!=null){
            Log.d(TAG, "onActivityResult: SUCCCCCESS");
            imageUri = data.getData();
            uploadPicture(imageUri);
        }
    }

    private void uploadPicture(Uri imageUri) {

        StorageReference mountainsRef = storageReference.child("mountains.jpg");
        String ref = "/" + UUID.randomUUID().toString();
        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageReference.child("images/" + profile.getUUID() + ref);

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());     // false
        UploadTask task = mountainImagesRef.putFile(imageUri);
        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //String downloadURL = task.getResult().getStorage().getDownloadUrl().toString();
               // Image image = new Image("",new ArrayList<>(), "images/" + profile.getUUID() + ref);
                profile.getImages().add(mountainImagesRef.toString());
                changeFirebaseProfile(profile);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void changeFirebaseProfile(Profile profile) {
        HashMap<String, Object> updateImageRefs = new HashMap<>();
        updateImageRefs.put("images", profile.getImages());
        db.collection("Profile").document(profile.getDocumentID())
                .update(updateImageRefs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: SUCCDFSDFSDFSDFSDFSDFSDFSDFSDFSDFSDF");
            }
        });
    }

    ProfileFragment.ProfileListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProfileFragment.ProfileListener) (context);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    interface ProfileListener{
        void gotoLoginFragmentafterLogout();

    }

    private class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder>{
        @NonNull
        @Override
        public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_item, parent, false);
            return new ProfileHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {
            holder.setupProfile(position);
        }

        @Override
        public int getItemCount() {
            return profile.getImages().size();
        }

        public class ProfileHolder extends RecyclerView.ViewHolder {
            ImageView imagePicture, imageTrash, imageLike;
            TextView textViewamountLikes;

            public ProfileHolder(@NonNull View itemView) {
                super(itemView);
                imagePicture = itemView.findViewById(R.id.imageView);
                imageTrash = itemView.findViewById(R.id.imageViewtrash);
                imageLike = itemView.findViewById(R.id.imageViewLike);
                textViewamountLikes = itemView.findViewById(R.id.textViewLike);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO when item is clicked. Go to Comments Fragment
                    }
                });
            }

            public void setupProfile(int position) {
                String str = profile.getImages().get(position);
                Log.d(TAG, "setupProfile: " + storageReference.toString());
                Log.d(TAG, "setupProfile: " + str);
                StorageReference imgRef = storageReference.child(str);
                Log.d(TAG, "setupProfile: " + imgRef.toString());
                Glide.with(getContext())
                        .load(imgRef)
                        .into(imagePicture);
                storageReference.child(str).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.d(TAG, "onSuccess: " + uri.toString());
                        Glide.with(getContext())
                                .load(uri)
                                .into(imagePicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d(TAG, "failed");
                    }
                });
            }
        }
    }
}
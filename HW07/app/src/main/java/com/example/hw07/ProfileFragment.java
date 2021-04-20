package com.example.hw07;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
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
    private ArrayList<Image> images = new ArrayList<>();

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
        getImages();
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
        return view;
    }

    private void getImages() {

        db.collection("Image").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    images.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: value) {
                        Image image = queryDocumentSnapshot.toObject(Image.class);
                        if (image.getPostedUUID().equals(profile.getUUID()))
                            images.add(image);
                    }
                    Log.d(TAG, "onEvent: " + images.toString());
                    recyclerView.setAdapter(adapter);
                }
            }
        });
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
        String imageLocation = "images/" + profile.getUUID() + ref;
        StorageReference mountainImagesRef = storageReference.child(imageLocation);

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());     // false
        UploadTask task = mountainImagesRef.putFile(imageUri);
        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //String downloadURL = task.getResult().getStorage().getDownloadUrl().toString();
               // Image image = new Image("",new ArrayList<>(), "images/" + profile.getUUID() + ref);
                addFirebaseImage(imageLocation);

            }
        });
    }

    private void addFirebaseImage(String imageLocation) {
        HashMap<String, Object> updateImageRefs = new HashMap<>();
        updateImageRefs.put("comments", new ArrayList<>());
        updateImageRefs.put("likedBy", new ArrayList<>());
        updateImageRefs.put("postedUUID", mAuth.getCurrentUser().getUid());
        updateImageRefs.put("storageRef", imageLocation);
        db.collection("Image").add(updateImageRefs).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                updateImageRefs.put("documentID", documentReference.getId());
                Log.d(TAG, "onSuccess: asdfasdfasdfasdfasd");
                db.collection("Image").document(documentReference.getId()).update(updateImageRefs);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: asdfasdfasdfasdf");
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
        void gotoCommentsFragmentFromProfileFragment(Image image);
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
            holder.position = position;
            holder.setupProfile(position);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public class ProfileHolder extends RecyclerView.ViewHolder {
            ImageView imagePicture, imageTrash, imageLike;
            TextView textViewamountLikes;
            int position;

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
                        mListener.gotoCommentsFragmentFromProfileFragment(images.get(position));
                    }
                });
            }

            public void setupProfile(int position) {
                Image image = images.get(position);

                //setting delete button
                if (image.getPostedUUID().equals(mAuth.getCurrentUser().getUid())){
                    imageTrash.setVisibility(View.VISIBLE);
                    imageTrash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO Delete
                            deleteImage(image);
                        }
                    });
                } else {
                    imageTrash.setVisibility(View.INVISIBLE);
                }

                textViewamountLikes.setText(image.getLikedBy().size() + " likes");

                if(image.getLikedBy().contains(mAuth.getCurrentUser().getUid())){
                    imageLike.setImageResource(R.drawable.like_favorite);
                } else {
                    imageLike.setImageResource(R.drawable.like_not_favorite);
                }

                imageLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(image.getLikedBy().contains(mAuth.getCurrentUser().getUid())){
                            image.getLikedBy().remove(mAuth.getCurrentUser().getUid());
                        } else {
                            image.getLikedBy().add(mAuth.getCurrentUser().getUid());
                        }
                        changeFirebaseData(image);
                        //adapter.notifyDataSetChanged();
                    }
                });

                StorageReference imgRef = storageReference.child(image.getStorageRef());
                imgRef.getBytes(1024*1024)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imagePicture.setImageBitmap(bitmap);
                            }
                        });
                Log.d(TAG, "setupProfile: " + imgRef.toString());
            }

            private void deleteImage(Image image) {
                //TODO Delete from Storage
                StorageReference reference = storageReference.child(image.getStorageRef());
                reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Image successfully deleted from storage");
                    }
                });
                //TODO Delete from Firestore
                db.collection("Image").document(image.getDocumentID()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "Image successfully deleted from firestore");
                            }
                        });
            }
        }
        private void changeFirebaseData(Image image) {
            HashMap<String, Object> likedByList = new HashMap<>();
            likedByList.put("likedBy", image.getLikedBy());
            db.collection("Image").document(image.getDocumentID())
                    .update(likedByList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Log.d(TAG, "LIKED BY SUCCESS");
                }
            });
        }
    }
}
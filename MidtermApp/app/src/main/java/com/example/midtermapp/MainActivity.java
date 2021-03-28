package com.example.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        CreateAccountFragment.RegisterListener, ForumsListFragment.ForumsListListener,
        CreateForumFragment.CreateForumListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rootView, new LoginFragment())
                .commit();
    }

    DataServices.AuthResponse mAuthResponse;

    @Override
    public void gotoForumsList(DataServices.AuthResponse authResponse) {
        this.mAuthResponse = authResponse;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, ForumsListFragment.newInstance(mAuthResponse))
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new CreateAccountFragment())
                .commit();
    }

    @Override
    public void gotoForumDetails(DataServices.Forum forum) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, ForumFragment.newInstance(forum, mAuthResponse))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logOut() {
        mAuthResponse = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoAddNewForum() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, CreateForumFragment.newInstance(mAuthResponse.getToken()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneCreateForum() {
        getSupportFragmentManager().popBackStack();
    }
}
package com.example.hw06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        CreateAccountFragment.RegisterListener, FourmListFragment.ForumsListListener,
        NewForumFragment.CreateForumListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout, new LoginFragment())
                .commit();
    }

    DataServices.AuthResponse mAuthResponse;

    @Override
    public void gotoForumsList(DataServices.AuthResponse authResponse) {
        this.mAuthResponse = authResponse;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, FourmListFragment.newInstance(mAuthResponse))
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, new CreateAccountFragment())
                .commit();
    }

    @Override
    public void gotoForumDetails(DataServices.Forum forum) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, ForumFragment.newInstance(forum, mAuthResponse))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logOut() {
        mAuthResponse = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoAddNewForum() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout, NewForumFragment.newInstance(mAuthResponse.getToken()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneCreateForum() {
        getSupportFragmentManager().popBackStack();
    }
}
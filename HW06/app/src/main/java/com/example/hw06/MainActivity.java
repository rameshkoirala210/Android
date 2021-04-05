package com.example.hw06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, CreateAccountFragment.NewAccountListener, ForumListFragment.FourmsListener {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new ForumListFragment()).commit();
        }
    }

    @Override
    public void gotoFourmsFragmentfromLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ForumListFragment()).commit();
    }

    @Override
    public void gotoNewAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CreateAccountFragment()).commit();
    }

    @Override
    public void gotoFourmsFragmentfromRegister(String name) {
        ForumListFragment fragment = new ForumListFragment();
        fragment.setname(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }

    @Override
    public void gotoLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void gotoNewForumFragment(String name) {
        NewForumFragment fragment = new NewForumFragment();
        fragment.setname(name);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack(null).commit();
    }

    @Override
    public void gotoLoginFragmentafterLogout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }
}
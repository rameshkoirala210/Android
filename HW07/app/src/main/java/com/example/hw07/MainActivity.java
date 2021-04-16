package com.example.hw07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, CreateAccountFragment.NewAccountListener, ProfileFragment.ProfileListener, ProfileListFragment.ProfileListListener {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new ProfileListFragment()).commit();
        }
    }

    @Override
    public void gotoProfileFragmentfromRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ProfileListFragment()).commit();
    }

    @Override
    public void gotoLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void gotoProfileFragmentfromLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ProfileListFragment()).commit();
    }

    @Override
    public void gotoNewAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CreateAccountFragment()).commit();
    }

    @Override
    public void gotoLoginFragmentafterLogout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void gotoProfileFragment(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setProfile(profile);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack(null).commit();
    }
}
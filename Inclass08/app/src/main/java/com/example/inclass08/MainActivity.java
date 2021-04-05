package com.example.inclass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, CreateNewAccountFragment.NewAccountListener, ForumsFragment.FourmsListener {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.layout, new ForumsFragment()).commit();
        }
    }

    @Override
    public void gotoFourmsFragmentfromLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ForumsFragment()).commit();
    }

    @Override
    public void gotoNewAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CreateNewAccountFragment()).commit();
    }

    @Override
    public void gotoFourmsFragmentfromRegister(String name) {
        ForumsFragment fragment = new ForumsFragment();
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
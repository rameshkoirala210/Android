package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, CreateAccountFragment.NewAccountListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
    }


    @Override
    public void gotoContactFragmentfromRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ContactFragment()).commit();
    }

    @Override
    public void gotoLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void gotoContactFragmentfromLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ContactFragment()).commit();
    }

    @Override
    public void gotoNewAccountFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CreateAccountFragment()).addToBackStack(null).commit();
    }
}
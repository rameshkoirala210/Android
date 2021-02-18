package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener{

    DataServices.Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void setAccountGoToAccountFragment(DataServices.Account account) {
        this.account = account;
        //Log.d("aaa", "setAccountGoToAccountFragment: inhere");

        getSupportFragmentManager().beginTransaction().replace(R.id.layout, AccountFragment.newInstance(account)).commit();
    }
}
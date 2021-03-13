package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout, new ContactsListFragment()).commit();
    }

    @Override
    public void gotoContactetailsFrangment(String category) {

    }

    @Override
    public void delete() {

    }
}
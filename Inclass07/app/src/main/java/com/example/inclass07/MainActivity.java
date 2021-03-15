package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.ContactListListener, ContactDetailsFragment.ContactDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout, new ContactsListFragment()).commit();
    }

    @Override
    public void gotoContactdetailsFragment(int position, ArrayList<Contact> contacts) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.setDetails(position, contacts);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }

    @Override
    public void gotoNewContactFragment(ArrayList<Contact> contacts) {
        NewContactFragment fragment = new NewContactFragment();
        fragment.sentContacts(contacts);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new NewContactFragment()).commit();
    }

    @Override
    public void gobacktoContractlist() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new ContactsListFragment()).commit();
    }

    @Override
    public void gotoeditcontractfragment(int position,ArrayList<Contact> contacts) {
        EditContactFragment fragment = new EditContactFragment();
        fragment.editDetails(position, contacts);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }
}
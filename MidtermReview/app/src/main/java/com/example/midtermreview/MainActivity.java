package com.example.midtermreview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements UserFragment.UsersFragmentListener, FilterByState.StatesFragmentListener, ExternalSortAdapter.ExternalSortAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout, new UserFragment(), "UsersFragment").commit();
    }

    @Override
    public void showFilter() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new FilterByState()).addToBackStack(null).commit();
    }

    @Override
    public void showSort() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new SortFragment()).addToBackStack(null).commit();
    }

    @Override
    public void statePicked(String state) {
        UserFragment fragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");

        if(fragment != null){
            fragment.setState(state);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void itemSortSelected(String sortBy, String sortDirection) {
        UserFragment fragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");

        if(fragment != null){
            fragment.setSort(sortBy, sortDirection);
        }
        getSupportFragmentManager().popBackStack();
    }
}
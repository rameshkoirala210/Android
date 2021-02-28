package com.example.homework03;

/*
    Assignment # In Class Assignment 05
    File Name Main
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, AppCategoriesFragment.AppCategoryListener, AppListFragment.AppsListListener {
    String token;
    private static final String TAG = "TAG_MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void goToAppCategoryFragment(String token) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        this.token = token;
        fragment.sendToken(this.token);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).commit();
    }

    @Override
    public void gotoAppListFragment(String category) {
        Log.d(TAG, "MAIN" + category);
        AppListFragment fragment = new AppListFragment();
        fragment.setCategory(category, token);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack("CATEGORY").commit();
    }

    @Override
    public void logout() {
        this.token = null;
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void gotoAppDetailsFragment(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        fragment.setApp(app);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragment).addToBackStack("APPSLIST").commit();
    }
}
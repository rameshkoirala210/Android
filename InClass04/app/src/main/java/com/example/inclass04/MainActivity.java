package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, AccountFragment.AccountListener, UpdateAccountFragment.UpdateAccountListener{

    DataServices.Account account;
    private static final String TAG = "TAG_MAIN";
    private static final String GoToTag = "GotoAccount";
    private static final String UpdateTag = "Update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void setAccountGoToAccountFragment(DataServices.Account account) {
        this.account = account;
        Log.d(TAG, "setAccountGoToAccountFragment: inhere");
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, AccountFragment.newInstance(account), GoToTag).commit();
    }

    @Override
    public void goToUpdateAccountFragment(DataServices.Account account) {
        Log.d(TAG, "goToUpdateAccountFragment: inhere");
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, UpdateAccountFragment.newInstance(account)).addToBackStack(UpdateTag).commit();
    }

    @Override
    public void logout() {
        this.account = null;
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new LoginFragment()).commit();
    }

    @Override
    public void updatedNewAccount(DataServices.Account account) {
        Log.d(TAG, "updatedNewAccount: " + account.getName());
        this.account = account;

        AccountFragment fragment = (AccountFragment) getSupportFragmentManager().findFragmentByTag(GoToTag);
        if (fragment != null){
            fragment.updateAccount(account);
        }

        getSupportFragmentManager().popBackStack();
    }
}
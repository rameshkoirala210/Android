package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/*
    Assignment # In Class Assignment 03
    File Name Profile
    Full name of the student - Ramesh Koirala, Anirudh Shankar

 */

public class ProfileActivity extends AppCompatActivity {

    TextView name,email,id,dept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        id = (TextView) findViewById(R.id.id);
        dept = (TextView) findViewById(R.id.dept);


        Profile user = (Profile) getIntent().getSerializableExtra("USER");
        name.setText(user.name);
        email.setText(user.email);
        id.setText(user.id);
        dept.setText(user.department);
/*
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Name")){
            String name1 = getIntent().getStringExtra("Name");
            name.setText(name1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Email")){
            String email1 = getIntent().getStringExtra("Email");
            email.setText(email1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("ID")){
            String id1 = getIntent().getStringExtra("ID");
            id.setText(id1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Dept")){
            String dept1 = getIntent().getStringExtra("Dept");
            dept.setText(dept1);
        }

 */
    }
}
package com.example.practice03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "aa";
    TextView name, email, phone, mood, address;
    ImageView iName,iEmail,iPhone,iMood,iAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        name = (TextView) findViewById(R.id.textName);
        email = (TextView) findViewById(R.id.textEmail);
        phone = (TextView) findViewById(R.id.textPhone);
        mood = (TextView) findViewById(R.id.textMood);
        address = (TextView) findViewById(R.id.textAddress);
        iName = (ImageView) findViewById(R.id.imageName);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Name")){
            String name1 = getIntent().getStringExtra("Name");
            name.setText(name1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Email")){
            String email1 = getIntent().getStringExtra("Email");
            email.setText(email1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Address")){
            String address1 = getIntent().getStringExtra("Address");
            address.setText(address1);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Phone Number")){
            String phoneNumber = getIntent().getStringExtra("Phone Number");
            phone.setText(phoneNumber);
        }
        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("Mood")){
            String mood1 = getIntent().getStringExtra("Mood");
            mood.setText(mood1);
        }

        iName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: ");
        name.setText(data.getStringExtra("Data"));
    }
}
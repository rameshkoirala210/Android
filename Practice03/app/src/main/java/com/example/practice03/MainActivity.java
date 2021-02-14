package com.example.practice03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button submit;
    EditText name,email,address,phoneNumber,mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.buttonSummit);
        email = (EditText) findViewById(R.id.editEmail);
        address = (EditText) findViewById(R.id.editAddress);
        phoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        mood = (EditText) findViewById(R.id.editMood);
        name = (EditText) findViewById(R.id.editName);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                intent.putExtra("Name", String.valueOf(name.getText().toString()));
                intent.putExtra("Email", String.valueOf(email.getText().toString()));
                intent.putExtra("Address", String.valueOf(address.getText().toString()));
                intent.putExtra("Phone Number", String.valueOf(phoneNumber.getText().toString()));
                intent.putExtra("Mood", String.valueOf(mood.getText().toString()));

                startActivity(intent);
            }
        });
    }
}
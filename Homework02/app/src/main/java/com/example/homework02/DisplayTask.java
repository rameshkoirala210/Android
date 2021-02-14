package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayTask extends AppCompatActivity {
    TextView name,date,priority;
    Button cancel,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        name = (TextView) findViewById(R.id.textDisplayName);
        date = (TextView) findViewById(R.id.textDisplayDate);
        priority = (TextView) findViewById(R.id.textDisplayPriority);
        cancel = (Button) findViewById(R.id.Cancelbutton);
        delete = (Button) findViewById(R.id.Deletebutton);


        Profile user = (Profile) getIntent().getSerializableExtra("USER");
        name.setText(user.name);
        date.setText(user.date);
        priority.setText(user.priority);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleted = "";
                Intent intent = new Intent();

                intent.putExtra("Deleted", deleted);
                setResult(5, intent);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
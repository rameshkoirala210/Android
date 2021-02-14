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


        int index = getIntent().getIntExtra("index", 0);
        name.setText(MainActivity.list.get(index).name);
        date.setText(MainActivity.list.get(index).date);
        priority.setText(MainActivity.list.get(index).priority);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.list.remove(index);

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
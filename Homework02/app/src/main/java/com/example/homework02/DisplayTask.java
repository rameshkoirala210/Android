package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
/*
    Assignment # Homework02
    File Name DisplayTask
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/

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


//        int index = getIntent().getIntExtra("index", 0);
//        Calendar d1 = MainActivity.list.get(index).date;
//        name.setText(MainActivity.list.get(index).name);
//        String x = d1.get(Calendar.MONTH)+1 + "/" + d1.get(Calendar.DAY_OF_MONTH) + "/" + d1.get(Calendar.YEAR);
//        date.setText(x);
//        priority.setText(MainActivity.list.get(index).priority);

        Task task = (Task) getIntent().getSerializableExtra("UserArray");
        Calendar d1 = task.date;
        String x = d1.get(Calendar.MONTH)+1 + "/" + d1.get(Calendar.DAY_OF_MONTH) + "/" + d1.get(Calendar.YEAR);

        //simple formatter ..

        name.setText(task.name);
        date.setText(x);
        priority.setText(task.priority);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newTask", task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
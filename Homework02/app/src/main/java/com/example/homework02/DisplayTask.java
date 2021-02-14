package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class DisplayTask extends AppCompatActivity {
    TextView name,date,priority;
    Button cancel,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Todo Clicking the “DELETE” button should return the task object back as a result to the
        //Main Activity in order to be deleted from the list maintained in the Main activity. Then
        //finish the Display Task activity

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

        Task user = (Task) getIntent().getSerializableExtra("UserArray");
        Calendar d1 = user.date;
        String x = d1.get(Calendar.MONTH)+1 + "/" + d1.get(Calendar.DAY_OF_MONTH) + "/" + d1.get(Calendar.YEAR);

        name.setText(user.name);
        date.setText(x);
        priority.setText(user.priority);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newUser", user);
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
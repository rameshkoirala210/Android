package com.example.homework02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button viewTask, createTask;
    TextView numofTasks, upcomingTask;

    public static ArrayList<Profile> list = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To Do List");


        viewTask = (Button) findViewById(R.id.buttonViewTask);
        createTask = (Button) findViewById(R.id.buttonCreateTask);
        numofTasks = (TextView) findViewById(R.id.numberofTask);
        upcomingTask = (TextView) findViewById(R.id.upcomingTask);


        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivityForResult(intent,1);
            }
        });
        viewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialog();
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("Are you sure")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(MainActivity.this, DisplayTask.class);
//                                    Profile user = new Profile(name, date, priority);
//                                    intent.putExtra("USER", user);
//                                    startActivityForResult(intent,1);
//                                }
//                            })
//                            .setNegativeButton("Cancel", null);
//
//                    AlertDialog alert = builder.create();
//                    alert.show();

            }
        });

    }
    private void CreateAlertDialog(){
        String[] s = {"R", "X"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rand")
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        builder.create();
        builder.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Task")
//        Object[] objects = list.toArray();
//                .setItems(objects, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//
//                    }
//                })
//                .setNegativeButton("Cancel", null);
//
//        builder.create();
//        builder.show();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
     
        numofTasks.setText("you have " + list.size() + " tasks");
    }
}
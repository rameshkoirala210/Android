package com.example.homework02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
/*
    Assignment # Homework02
    File Name To Do list
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/

public class MainActivity extends AppCompatActivity {
    TextView numofTasks, upcomingTask;
    final public int REQ_CREATE_TASK = 100;
    final public int REQ_VIEW_TASK = 200;

    public ArrayList<Task> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To Do List");


        numofTasks = (TextView) findViewById(R.id.numberofTask);
        upcomingTask = (TextView) findViewById(R.id.upcomingTask);

        setText();
        findViewById(R.id.buttonCreateTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivityForResult(intent,REQ_CREATE_TASK);
            }
        });
        findViewById(R.id.buttonViewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialog();
            }
        });

    }
    private void CreateAlertDialog(){
        String[] s = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            s[i] = list.get(i).name;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(list.size() > 0) {
            builder.setTitle("Select Task")
                    .setItems(s, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, DisplayTask.class);
                            intent.putExtra("UserArray", list.get(which));
                            startActivityForResult(intent, REQ_VIEW_TASK);
                        }
                    });
        }else {
            builder.setTitle("No Tasks: Create a Task First");
        }
            builder.create();
            builder.show();


    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        numofTasks.setText("You have " + list.size() + " tasks!");
//        if (list.size() > 1)
//            Collections.sort(list);
//        if (list.size()>0) {
//            upcomingTask.setText(list.get(0).toString());
//
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Create Task
        if (requestCode == REQ_CREATE_TASK){
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra("createTask")) {
                    list.add((Task) data.getSerializableExtra("createTask"));
                }
            } else if (resultCode == RESULT_CANCELED) {}
        }
        if(requestCode == REQ_VIEW_TASK){
            if (resultCode == RESULT_OK) {
                for(int i = 0; i < list.size(); i++){
                    Task task = (Task) data.getSerializableExtra("newTask");
                    //Log.d("TAG", "onActivityResult: " + first.equals(second));
                    if(task.equals(list.get(i))){
                        list.remove(i);
                    }
                }

                list.remove((Task) data.getSerializableExtra("newUser"));
            } else if (resultCode == RESULT_CANCELED) {}
        }
        setText();
    }
    public void setText(){
        numofTasks.setText("You have " + list.size() + " tasks!");
        if (list.size() > 1)
            Collections.sort(list);
        if (list.size()>0) {
            upcomingTask.setText(list.get(0).toString());
        }else if(list.size() == 0){
            upcomingTask.setText("Upcoming Tasks" + "\n\nNone");
        }
    }
}
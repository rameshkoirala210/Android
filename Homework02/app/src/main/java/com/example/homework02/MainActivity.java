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

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    //Todo: Show "None" when there is no task
    //Todo: Upon returning from the “Create Task Activity” the newly created task should be
    //stored in the task list that is stored in the Main Activity. In addition, the number of
    //tasks and upcoming task displayed should be updated to account for the newly
    //added task
    Button viewTask, createTask;
    TextView numofTasks, upcomingTask;

    public static ArrayList<Task> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To Do List");


        viewTask = (Button) findViewById(R.id.buttonViewTask);
        createTask = (Button) findViewById(R.id.buttonCreateTask);
        numofTasks = (TextView) findViewById(R.id.numberofTask);
        upcomingTask = (TextView) findViewById(R.id.upcomingTask);

        setText();
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
            }
        });

    }
    private void CreateAlertDialog(){

        String[] s = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            s[i] = list.get(i).name;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Task")
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent intent = new Intent(MainActivity.this, DisplayTask.class);
                        //intent.putExtra("index", which);
                        intent.putExtra("UserArray", list.get(which));
                        startActivityForResult(intent,2);
                    }
                });
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
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra("User")) {
                    list.add((Task) data.getSerializableExtra("User"));
                }
            } else if (resultCode == RESULT_CANCELED) {}
        }
        if(requestCode == 2){
            //MainActivity.list.remove(index);
            if (resultCode == RESULT_OK) {
                Task t = ((Task)data.getSerializableExtra("newUser"));
                list.remove(t);
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
            upcomingTask.setText("None");
        }
    }
}
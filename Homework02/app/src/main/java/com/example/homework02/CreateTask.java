package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
/*
    Assignment # Homework02
    File Name CreatTask
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Todo Validation: should have a name, date, and a priority
    RadioGroup group;
    RadioButton selectedButton;
    Button submit, cancel, setDate;
    EditText taskName;
    TextView date;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle("Create Task");

        group = (RadioGroup) findViewById(R.id.RadiogroupPriority);
        submit = (Button) findViewById(R.id.Deletebutton);
        cancel = (Button) findViewById(R.id.Cancelbutton);
        setDate = (Button) findViewById(R.id.buttonSetDate);
        taskName = (EditText) findViewById(R.id.editTextName);
        date = (TextView) findViewById(R.id.ViewDate);


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPickerDailog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){

                    int radioId = group.getCheckedRadioButtonId();
                    selectedButton = findViewById(radioId);

                    String enteredName = taskName.getText().toString();
                    Calendar enteredDate = calendar;

                    if(validate(enteredName,date.getText().toString(), group)) {
                        String enteredData = selectedButton.getText().toString();
                        Task task = new Task(enteredName, enteredDate, enteredData);

                        Intent intent = new Intent();
                        intent.putExtra("createTask", task);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
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
    private  void showDataPickerDailog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date1 = (month + 1)  + "/" + dayOfMonth + "/" + year;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        calendar = cal;
        date.setText(date1);
    }
    private boolean validate(String name, String date, RadioGroup group) {
        if (name.equals("")) {
            Toast.makeText(this, "Task Empty! Enter Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        int radioId = group.getCheckedRadioButtonId();
        if (radioId==-1){
            Toast.makeText(this, "Select an Option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (date.equals("")){
            Toast.makeText(this, "Select a Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
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

public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    RadioGroup group;
    RadioButton selectedButton;
    Button submit, cancel, setDate;
    EditText taskName;
    TextView date;

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
            public void onClick(View v) {
                int radioId = group.getCheckedRadioButtonId();
                selectedButton = findViewById(radioId);

                String enteredName = taskName.getText().toString();
                String enteredDate = date.getText().toString();
                String enteredData = selectedButton.getText().toString();

                Profile user = new Profile(enteredName, enteredDate, enteredData);
                MainActivity.list.add(user);


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
    private  void showDataPickerDailog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date1 = (month + 1)  + "/" + dayOfMonth + "/" + year;
        date.setText(date1);
    }
}
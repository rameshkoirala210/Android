package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/*
    Assignment # In Class Assignment 03
    File Name: Department
    Full name of the student - Ramesh Koirala, Anirudh Shankar

 */
public class DepartmentActivity extends AppCompatActivity {

    RadioGroup group;
    RadioButton selectedButton;
    Button select, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        setTitle("Department");

        group = (RadioGroup) findViewById(R.id.radioGroup);
        select = (Button) findViewById(R.id.depButtonSelect);
        cancel = (Button) findViewById(R.id.buttonCancel);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = group.getCheckedRadioButtonId();
                if (radioId==-1){
                    Toast.makeText(DepartmentActivity.this, "Select One Option", Toast.LENGTH_SHORT).show();
                } else {
                    selectedButton = findViewById(radioId);
                    String enteredData = selectedButton.getText().toString();
                    Intent intent = new Intent();

                    intent.putExtra("Data", enteredData);
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
}
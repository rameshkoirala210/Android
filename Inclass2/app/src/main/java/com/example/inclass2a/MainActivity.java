package com.example.inclass2a;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.lang.String;
/*
Assignment # InClass Assignment 2
File Name: In Class 2a
Full name of the Students: Ramesh Koirala, Anirudh Shankar
 */
public class MainActivity extends AppCompatActivity {
    EditText inches;
    TextView result;
    Button meters, feet, miles, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inches = (EditText) findViewById(R.id.editInches);
        result = (TextView) findViewById(R.id.textResult);
        meters = (Button) findViewById(R.id.buttonMeters);
        feet = (Button) findViewById(R.id.buttonFeet);
        miles = (Button) findViewById(R.id.buttonMiles);
        clear = (Button) findViewById(R.id.buttonClear);


        meters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInches(inches.getText().toString())) {
                    Double value = Double.parseDouble(inches.getText().toString());
                    Double calculateValue = value / 39.37;
                    result.setText(calculateValue.toString() + " meters");
                }
            }
        });
        feet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInches(inches.getText().toString())) {
                    Double value = Double.parseDouble(inches.getText().toString().trim());
                    Double calculateValue = value / 12.0;
                    result.setText(calculateValue.toString() + " feet");
                }
            }
        });
        miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInches(inches.getText().toString())) {
                    Double value = Double.parseDouble(inches.getText().toString());
                    Double calculateValue = value / 63360.0;
                    result.setText(calculateValue.toString() + " miles");
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inches.setText("");
                result.setText("");
            }
        });

    }
    private boolean validateInches(String str) {
        if (str.trim().isEmpty() || str.equals("")) {
            Toast.makeText(this, "ENTER INCHES", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Double.parseDouble(str);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Check Inches. CHANGE IT", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
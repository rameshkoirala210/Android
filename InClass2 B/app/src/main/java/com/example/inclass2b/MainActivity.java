package com.example.inclass2b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
Assignment # In Class Assignment 2
File Name: In Class 2B
Full name of the Students: Ramesh Koirala, Anirudh Shankar
 */

public class MainActivity extends AppCompatActivity {

    EditText inches;
    TextView result;
    RadioButton checkedbutton;
    RadioGroup radioGroup;
    Button convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inches = (EditText) findViewById(R.id.Inches);
        result = (TextView) findViewById(R.id.result);
        convert = (Button) findViewById(R.id.Convert);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                checkedbutton = findViewById(radioID);
                if (checkedbutton.getText().toString().equals("Clear All")) {
                    inches.setText("");
                    result.setText("");
                } else if (validateInches(inches.getText().toString().trim())){
                    if (checkedbutton.getText().toString().equals("To Meter")) {
                        //result.setText(checkedbutton.getText().toString())
                        Double value = Double.parseDouble(inches.getText().toString());
                        Double calculateValue = value / 39.37;
                        result.setText(calculateValue.toString() + " meters");
                    } else if (checkedbutton.getText().toString().equals("To Feet")) {
                        Double value = Double.parseDouble(inches.getText().toString().trim());
                        Double calculateValue = value / 12.0;
                        result.setText(calculateValue.toString() + " feet");
                    } else if (checkedbutton.getText().toString().equals("To Miles")) {
                        Double value = Double.parseDouble(inches.getText().toString());
                        Double calculateValue = value / 63360.0;
                        result.setText(calculateValue.toString() + " miles");
                    }
                }
            }
        });

    }

    private boolean validateInches(String str) {
        if (str.trim().isEmpty() || str.equals("")) {
            Toast.makeText(this, "ENTER INCHES", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            for (int i = 0; i < str.length(); i++){
                if (Character.isDigit(str.charAt(i)) == false){
                    Toast.makeText(this, "Check Inches. CHANGE IT", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
           /* try {
                //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Double.parseDouble(str);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Check Inches. CHANGE IT", Toast.LENGTH_SHORT).show();
                return false;
            }*/
        return true;
    }
}
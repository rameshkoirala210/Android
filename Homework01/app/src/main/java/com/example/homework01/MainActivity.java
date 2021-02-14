package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.homework01.R;

public class MainActivity extends AppCompatActivity {

    EditText billTotal;
    TextView result;
    RadioButton selectedButton;
    RadioGroup group;
    SeekBar tipSeekBar;
    SeekBar percantageSeekBar;
    TextView tipPercent;
    TextView displayTipBar;
    TextView totalNumBottom;
    //ConstraintLayout ten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billTotal = (EditText)findViewById(R.id.billTotal);
        tipSeekBar=findViewById(R.id.tipSeekBar);
        percantageSeekBar = findViewById(R.id.tipSeekBar);
        displayTipBar = findViewById(R.id.displayTipBar);
        tipPercent = findViewById(R.id.tipPercent);
        totalNumBottom=findViewById(R.id.totalNumBottom);
        group = (RadioGroup) findViewById(R.id.group);
        // Double finalBill = Double.parseDouble(billTotal.getText().toString());

        // ten = findViewById(R.id.ten);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioID = group.getCheckedRadioButtonId();
                selectedButton = findViewById(radioID);


                if (checkedId == R.id.ten) {
                    Double value = Double.parseDouble(billTotal.getText().toString());
                    Double finalizedBill=value*1.1;
                    //result.setText(totalNumBottom.toString());
                    totalNumBottom.setText(totalNumBottom.toString());
                }
                else if (checkedId == R.id.fifteen) {
                    //result.setText(checkedbutton.getText().toString())
                    Double value = Double.parseDouble(billTotal.getText().toString());
                    Double finalizedBill=value*1.15;
                    totalNumBottom.setText(totalNumBottom.toString());
                } else if (checkedId == R.id.eighteen) {
                    Double value = Double.parseDouble(billTotal.getText().toString());
                    Double finalizedBill=value*1.18;
                    //  result.setText(totalNumBottom.toString());
                    totalNumBottom.setText(totalNumBottom.toString());

                }
            }
        });

    }
}
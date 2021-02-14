package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
/*
Assignment # Homework 1
File Name: HW01
Full name of the Students: Ramesh Koirala, Anirudh Shankar
 */
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
    Button btnExit;
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
        btnExit = (Button) findViewById(R.id.btnExit);
     // Double finalBill = Double.parseDouble(billTotal.getText().toString());
       // ten = findViewById(R.id.ten);
        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent.setText(String.valueOf(progress) + "%");
                if (group.getCheckedRadioButtonId() == R.id.customButton){
                    if (validate(billTotal.getText().toString())) {
                        Double bill = Double.parseDouble(billTotal.getText().toString());
                        Double tipsAmount = bill*progress*0.01;
                        displayTipBar.setText(String.valueOf(tipsAmount));
                        Double total = bill + tipsAmount;
                        totalNumBottom.setText(String.valueOf(total));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioID = group.getCheckedRadioButtonId();
                if (validate(billTotal.getText().toString())){
                    if (radioID == R.id.ten){
                        Double totalBill = Double.parseDouble(billTotal.getText().toString());
                        Double tipsAmount = totalBill * .10;
                        Double totalAmount = totalBill+tipsAmount;

                        displayTipBar.setText(String.valueOf(tipsAmount));
                        totalNumBottom.setText(String.valueOf(totalAmount));
                    } else if (radioID == R.id.fifteen){
                        Double totalBill = Double.parseDouble(billTotal.getText().toString());
                        Double tipsAmount = totalBill * .15;
                        Double totalAmount = totalBill+tipsAmount;

                        displayTipBar.setText(String.valueOf(tipsAmount));
                        totalNumBottom.setText(String.valueOf(totalAmount));
                    } else if (radioID == R.id.eighteen){
                        Double totalBill = Double.parseDouble(billTotal.getText().toString());
                        Double tipsAmount = totalBill * .18;
                        Double totalAmount = totalBill+tipsAmount;

                        displayTipBar.setText(String.valueOf(tipsAmount));
                        totalNumBottom.setText(String.valueOf(totalAmount));
                    } else if (radioID == R.id.customButton){
                        //done elsewhere
                    }

                }
            }
        });

       btnExit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       /*
       group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {

               int radioID = group.getCheckedRadioButtonId();
               selectedButton = findViewById(radioID);
               //validate(billTotal.getText().toString());
               if(validate(billTotal.getText().toString())) {
                   if (checkedId == R.id.ten) {
                       Double value = Double.parseDouble(billTotal.getText().toString());
                       Double finalizedBill = value * 1.1;
                       Double tip = value * .1;
                       //result.setText(totalNumBottom.toString());
                       displayTipBar.setText(tip.toString());

                       totalNumBottom.setText(finalizedBill.toString());
                   } else if (checkedId == R.id.fifteen) {
                       //result.setText(checkedbutton.getText().toString())
                       Double value = Double.parseDouble(billTotal.getText().toString());
                       Double finalizedBill = value * 1.15;
                       Double tip = value * .15;
                       displayTipBar.setText(tip.toString());
                       totalNumBottom.setText(finalizedBill.toString());
                   } else if (checkedId == R.id.eighteen) {
                       Double value = Double.parseDouble(billTotal.getText().toString());
                       Double finalizedBill = value * 1.18;
                       Double tip = value * .18;
                       //  result.setText(totalNumBottom.toString());
                       displayTipBar.setText(tip.toString());
                       totalNumBottom.setText(finalizedBill.toString());

                   } else if (checkedId == R.id.customButton) {
                       tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                           @Override
                           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                               Double realProgress = new Double(progress);
                               String valueEntered = billTotal.getText().toString();
                               double finalBill = Double.parseDouble(valueEntered);
                               double tip = progress;
                               //Double finalizedBill = finalBill*tip*.01;
                               realProgress = (finalBill * progress) * .01;
                               Double finalizedBill = finalBill + realProgress;
                               tipPercent.setText(String.valueOf(progress) + "%");

                               if (group.getCheckedRadioButtonId() == R.id.customButton) {
                                   displayTipBar.setText(String.valueOf(realProgress));
                                   totalNumBottom.setText(finalizedBill.toString());
                               }
                           }
                           @Override
                           public void onStartTrackingTouch(SeekBar seekBar) {

                           }
                           @Override
                           public void onStopTrackingTouch(SeekBar seekBar) {
                           }
                       });
                   }
               }
           }

       });
       */
    }
    private boolean validate(String str) {
        if (str.trim().isEmpty() || str.equals("")) {
            Toast.makeText(this, "Enter Bill Total", Toast.LENGTH_SHORT).show();
            displayTipBar.setText("");
            totalNumBottom.setText("");
            return false;
        } else {
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i)) == false) {
                    Toast.makeText(this, "Incorrect Bill Value", Toast.LENGTH_SHORT).show();
                    displayTipBar.setText("");
                    totalNumBottom.setText("");
                    return false;
                }
            }
        }
        return true;
    }
}

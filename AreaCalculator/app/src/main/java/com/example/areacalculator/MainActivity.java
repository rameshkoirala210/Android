package com.example.areacalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.lang.String;

public class MainActivity extends AppCompatActivity {

    EditText mEditLength1;
    EditText mEditLength2;
    TextView mTextResult, mEditSelection, mLengthtext;
    Button mButtonCalculator, mButtonClear;
    ImageView mTriangle, mSquare, mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditLength1 = (EditText) findViewById(R.id.editLength1);
        mEditLength2 = (EditText) findViewById(R.id.editLength2);
        mTextResult = (TextView) findViewById(R.id.textResult);
        mEditSelection = (TextView) findViewById(R.id.editSelection);
        mLengthtext = (TextView) findViewById(R.id.Lengthtext);
        mButtonCalculator = (Button) findViewById(R.id.buttonCalculator);
        mButtonClear = (Button) findViewById(R.id.buttonClear);
        mTriangle = (ImageView) findViewById(R.id.Triangle);
        mSquare = (ImageView) findViewById(R.id.Square);
        mCircle = (ImageView) findViewById(R.id.Circle);

        mCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSelection.setText("Circle");
                mEditLength2.setVisibility(-1);
                mLengthtext.setVisibility(-1);
            }
        });
        mSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSelection.setText("Square");
                mEditLength2.setVisibility(-1);
                mLengthtext.setVisibility(-1);
            }
        });
        mTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditSelection.setText("Triangle");
                mEditLength2.setVisibility(0);
                mLengthtext.setVisibility(0);
            }
        });
        mButtonCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditLength2.setVisibility(0);
                mLengthtext.setVisibility(0);
                mEditSelection.setText("Select a Shape");
                mTextResult.setText("Result");
                mEditLength1.setText("");
                mEditLength2.setText("");
            }
        });
    }
    public void calculate(){
        if (mEditSelection.getText().toString() == "Square") {
            Double value1 = Double.parseDouble(mEditLength1.getText().toString());
            Double calculateValue = value1 * value1;
            mTextResult.setText(calculateValue.toString());
        }
        else if(mEditSelection.getText().toString() == "Triangle"){
            Double value1 = Double.parseDouble(mEditLength1.getText().toString());
            Double value2 = Double.parseDouble(mEditLength2.getText().toString());
            Double calculateValue = .5 * value1 * value2;
            mTextResult.setText(calculateValue.toString());
        }
        else if(mEditSelection.getText().toString() == "Circle"){
            Double value1 = Double.parseDouble(mEditLength1.getText().toString());
            Double calculateValue =  Math.PI * (value1 * value1);
            mTextResult.setText(calculateValue.toString());
        }

    }

}
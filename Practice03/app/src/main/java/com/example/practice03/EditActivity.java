package com.example.practice03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    Button save;
    EditText change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        save = (Button) findViewById(R.id.buttonSave);
        change = (EditText) findViewById(R.id.editChange);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredData = change.getText().toString();
                Intent intent = new Intent();

                intent.putExtra("Data", enteredData);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
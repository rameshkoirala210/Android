package com.example.inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
    Assignment # In Class Assignment 03
    File Name Registration
    Full name of the student - Ramesh Koirala, Anirudh Shankar

 */
public class MainActivity extends AppCompatActivity {

    Button submit, select;
    EditText name,email,id;
    TextView dep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Registration");

        submit = (Button) findViewById(R.id.buttonSubmit);
        select = (Button) findViewById(R.id.buttonSelect);
        email = (EditText) findViewById(R.id.userEmail);
        name = (EditText) findViewById(R.id.userName);
        id = (EditText) findViewById(R.id.userID);
        dep = (TextView) findViewById(R.id.userDept);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(name.getText().toString(), email.getText().toString(), id.getText().toString(), dep.getText().toString())) {

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    Profile user = new Profile(name.getText().toString(), email.getText().toString(), id.getText().toString(), dep.getText().toString());
                    intent.putExtra("USER", user);
                    /*
                    intent.putExtra("Name", String.valueOf(name.getText().toString()));
                    intent.putExtra("Email", String.valueOf(email.getText().toString()));
                    intent.putExtra("ID", String.valueOf(id.getText().toString()));
                    intent.putExtra("Dept", String.valueOf(dep.getText().toString()));
                    */
                    startActivity(intent);
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private boolean validate(String name, String email, String id, String department) {

        if (name.equals("")){
            Toast.makeText(this, "Name Empty! Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i = 0; i < name.toCharArray().length; i++){
            if(Character.isLetter(name.charAt(i)) || Character.isWhitespace(name.charAt(i))){

            }else{
                Toast.makeText(this, "Name Not Valid", Toast.LENGTH_SHORT).show();
                return  false;
            }
        }
        if (email.equals("")){
            Toast.makeText(this, "Email Empty! Enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            Toast.makeText(this, "Email Not Valid", Toast.LENGTH_SHORT).show();
        }
        if (id.equals("")){
            Toast.makeText(this, "ID Empty! Enter id", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i = 0; i < id.toCharArray().length; i++) {
            if (Character.isDigit(id.charAt(i))) {

            } else {
                Toast.makeText(this, "ID not Valid", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (department.equals("")){
            Toast.makeText(this, "Department Empty! Select Department", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            dep.setText(data.getStringExtra("Data"));
        }
    }

}
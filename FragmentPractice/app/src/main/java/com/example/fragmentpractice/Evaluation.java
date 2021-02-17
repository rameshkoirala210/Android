package com.example.fragmentpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Evaluation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Evaluation extends Fragment {
    EditText nickname;
    TextView textViewStructure,textViewdeadline,textViewassignment;
    RadioButton selectedButton;
    RadioGroup group;
    SeekBar structure,deadline,course;
    ImageView image;
    Button submit;


    public Evaluation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_evaluation, container, false);

        nickname = (EditText)v.findViewById(R.id.editnickname);
        structure = v.findViewById(R.id.seekBarStructure);
        deadline = v.findViewById(R.id.seekBardeadline);
        course = v.findViewById(R.id.seekBarcourse);
        textViewStructure = v.findViewById(R.id.textViewStructure);
        textViewdeadline = v.findViewById(R.id.textViewdeadline);
        textViewassignment= v.findViewById(R.id.textViewassignment);
        group = (RadioGroup) v.findViewById(R.id.group);
        image = (ImageView) v.findViewById(R.id.selectedImage);
        submit = (Button) v.findViewById(R.id.buttonSubmit);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newfragment fragment = new newfragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
            }
        });
        structure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int coursestructure = progress;
                textViewStructure.setText(idk(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        deadline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int assignmentDeadlines = progress;
                textViewdeadline.setText(idk(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        course.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int courseassignment = progress;
                textViewassignment.setText(idk(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioID = group.getCheckedRadioButtonId();
                selectedButton = v.findViewById(radioID);
                String program = selectedButton.getText().toString();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
    public String idk(int number){
        if (number == 1){
            return "Strongly Disagree";
        }else if(number == 2){
            return "Disagree";
        }else if(number == 3){
            return "Neutral";
        }else if(number == 4){
            return "Agree";
        }else if(number == 5){
            return "Strongly Agree";
        }
        return "failed";
    }
}
package com.example.inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "TAG_MAIN";
    ListView listViewnumbers;
    TextView textViewComplexity, textViewProgress,textViewAverage;
    SeekBar seekBarSelect;
    ProgressBar progressBar;
    ExecutorService threadpool;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewnumbers = findViewById(R.id.ListViewnumbers);
        textViewComplexity = findViewById(R.id.textViewComplexity);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewAverage = findViewById(R.id.textViewAverage);
        seekBarSelect = findViewById(R.id.seekBarSelect);
        progressBar = findViewById(R.id.progressBar);


        textViewComplexity.setText("1 Times");
        seekBarSelect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewComplexity.setText(progress + " Times ");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        threadpool = Executors.newFixedThreadPool(2);

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = textViewComplexity.getText().toString().split(" ");
                Integer number = Integer.parseInt(str[0]);

                threadpool.execute(new doThreadwork(number));
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        return false;
                    }
                });
            }
        });
        findViewById(R.id.buttonAsyncTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: get number from seekbar and send it to doAsyncTask
                String[] str = textViewComplexity.getText().toString().split(" ");
                Integer number = Integer.parseInt(str[0]);
                new doAsyncTask().execute(number);
            }
        });
    }
    class doThreadwork implements Runnable {
        Integer num;
        public doThreadwork(Integer num) {
            this.num = num;
        }
        //TODO return using handaler
        public void run() {

        }
    }
    class doAsyncTask extends AsyncTask<Integer, Double, ArrayList<Double> >{
        ArrayList<Double> randomNumbers = new ArrayList<>();
        ArrayAdapter<Double> adapter;

        @Override
        protected void onPreExecute() {
            adapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, randomNumbers);
            listViewnumbers.setAdapter(adapter);
        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            int number = integers[0];
            for(int i = 0; i < number; i++){
                randomNumbers.add(HeavyWork.getNumber());
                publishProgress(i+1.0, number + 0.0);
            }
            return randomNumbers;
        }
        @Override
        protected void onProgressUpdate(Double... values) {
            progressBar.setMax(values[1].intValue());
            progressBar.setProgress(values[0].intValue());
            textViewProgress.setText(values[0].intValue() + "/" + values[1].intValue());

            adapter.notifyDataSetChanged();

            Double total = 0.0;
            for (int i=0; i< randomNumbers.size(); i++) {
                total += randomNumbers.get(i);
            }
            textViewAverage.setText("Average: " + total / randomNumbers.size());
        }
    }
}
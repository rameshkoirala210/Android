package com.example.inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
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
import java.lang.reflect.Array;
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
        ArrayList<Double> randomNums = new ArrayList<>();

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = textViewComplexity.getText().toString().split(" ");
                Integer number = Integer.parseInt(str[0]);
                Log.d(TAG, "onClick: In thread");
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        switch (msg.what){
                            case doThreadwork.STATUS_START:
                                Log.d(TAG, "handleMessage: Start Progress");
                                findViewById(R.id.buttonAsyncTask).setEnabled(false);
                                findViewById(R.id.buttonThread).setEnabled(false);
                                break;

                            case doThreadwork.STATUS_STOP:
                                Log.d(TAG, "handleMessage: Stop Progress");
                                findViewById(R.id.buttonAsyncTask).setEnabled(true);
                                findViewById(R.id.buttonThread).setEnabled(true);
                                break;

                            case doThreadwork.STATUS_PROGRESS:
                                Log.d(TAG, "handleMessage: Status Progress");

                                Double total = 0.0;
                                for (int i = 0; i < randomNums.size(); i++) {
                                    total += randomNums.get(i);
                                }
                                textViewAverage.setText("Average: " + total / randomNums.size());

                                progressBar.setMax(number);
                                progressBar.setProgress(msg.arg1);
                                textViewProgress.setText(msg.arg1 + "/" + number);

                                ArrayAdapter<Double> adapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, randomNums);
                                listViewnumbers.setAdapter(adapter);
                                break;
                        }
                        return false;
                    }
                });
                threadpool.execute(new doThreadwork(number, randomNums));
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
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        Integer num;
        ArrayList<Double> randomNumbers;
        public doThreadwork(Integer num, ArrayList<Double> randomNums) {
            this.num = num;
            this.randomNumbers = randomNums;
        }
        //TODO return using handaler
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);

            randomNumbers.clear();
            for(int i = 0; i < num; i++){
                Message message = new Message();
                message.what = STATUS_PROGRESS;
                randomNumbers.add(HeavyWork.getNumber());
                message.arg1 = i+1;
                handler.sendMessage(message);
            }


            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
        }
    }
    class doAsyncTask extends AsyncTask<Integer, Double, ArrayList<Double> >{
        ArrayList<Double> randomNumbers = new ArrayList<>();
        ArrayAdapter<Double> adapter;

        @Override
        protected void onPreExecute() {
            adapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, randomNumbers);
            listViewnumbers.setAdapter(adapter);
            findViewById(R.id.buttonAsyncTask).setEnabled(false);
            findViewById(R.id.buttonThread).setEnabled(false);
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

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            super.onPostExecute(doubles);
            findViewById(R.id.buttonThread).setEnabled(true);
            findViewById(R.id.buttonAsyncTask).setEnabled(true);
        }
    }
}
package com.example.concurrencypractice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    public final String TAG = "TAG_MAIN";
    Button buttonThread, buttonAsyncTask;
    ImageView imageViewOne;
    ProgressBar progressBarLoading;
    String urlLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonThread = findViewById(R.id.buttonThread);
        buttonAsyncTask = findViewById(R.id.buttonAsyncTask);
        imageViewOne = findViewById(R.id.imageViewOne);
        progressBarLoading = findViewById(R.id.progressBarLoading);

        buttonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlLink = "https://cdn.pixabay.com/photo/2017/12/31/06/16/boats-3051610_960_720.jpg";

            }
        });
        buttonAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlLink = "https://cdn.sofifa.com/flags/fr.png";
                new LoadImage(imageViewOne).execute(urlLink);
            }
        });

    }
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageViewOne;
        public LoadImage(ImageView imageViewOne) {
            this.imageViewOne = imageViewOne;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                Log.d(TAG, "doInBackground: d1");
                URL url = new URL(urlLink);
                Log.d(TAG, "doInBackground: d2" + url.toString());
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.d(TAG, "doInBackground: " + "HERE");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Bitmap Error");
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.d("TAG", "doInBackground: onPost Error");
            if (bitmap != null)
            imageViewOne.setImageBitmap(bitmap);
        }
    }

}
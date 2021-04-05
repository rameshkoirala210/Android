package com.example.hw05;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/*
    Assignment # Homework-05
    File Name  Weather Forecast Adapter
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class WeatherForecastRecycler extends RecyclerView.Adapter<WeatherForecastRecycler.ForecastViewHolder> {
    private static String TAG = "TAG-Rforecast";
    ArrayList<Weather> weathers = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    String url;

    public WeatherForecastRecycler(ArrayList<Weather> weathers) {
        this.weathers = weathers;
        //Log.d(TAG, "WeatherForecastRecycler: CONSTRUCTOR CHECK");
        Log.d(TAG, "WeatherForecastRecycler: "+ weathers);
    }

    @NonNull
    @Override
    public WeatherForecastRecycler.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_recyclerview, parent, false);
        ForecastViewHolder forecastViewHolder = new ForecastViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: Inside OnCreateVIEW HOLDER");
        return forecastViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.textViewRHumidity.setText("Humidity: " +weathers.get(position).humidity + "%");
        holder.textViewRTemp.setText(weathers.get(position).temprature +"F");
        holder.textViewRMaxTemp.setText("Max: " + weathers.get(position).tempratureMax +"F");
        holder.textViewRMinTemp.setText("Min: " + weathers.get(position).tempratureMin + "F");
        holder.textViewRClouds.setText(weathers.get(position).description);
        holder.textViewRDate.setText(weathers.get(position).date);
        String imgUrl = "https://openweathermap.org/img/wn/" + weathers.get(position).icon +"@2x.png";
        Log.d(TAG, "onCreateView: " + imgUrl);
        Picasso.get().load(imgUrl).into(holder.imageViewforecast);

    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }


    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        ArrayList<Weather> weathers = new ArrayList<>();
        ImageView imageViewforecast;
        TextView textViewRDate, textViewRTemp, textViewRMaxTemp, textViewRMinTemp, textViewRHumidity, textViewRClouds;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ForecastViewHolder: INSIDE FORECASTVIEWHOLDER CONS");
            imageViewforecast = itemView.findViewById(R.id.imageViewforecast);
            textViewRDate = itemView.findViewById(R.id.textViewRDate);
            textViewRTemp = itemView.findViewById(R.id.textViewRTemp);
            textViewRMaxTemp = itemView.findViewById(R.id.textViewRMaxTemp);
            textViewRMinTemp = itemView.findViewById(R.id.textViewRMinTemp);
            textViewRHumidity = itemView.findViewById(R.id.textViewRHumidity);
            textViewRClouds = itemView.findViewById(R.id.textViewRClouds);
        }
    }
}
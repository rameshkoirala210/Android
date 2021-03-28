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

public class WeatherForecastRecycler extends RecyclerView.Adapter<WeatherForecastRecycler.ForecastViewHolder> {
    private static String TAG = "TAG-Rforecast";
    ArrayList<Weather> weathers = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    String url;

    public WeatherForecastRecycler(String url) {
        this.url = url;
        Log.d(TAG, "WeatherForecastRecycler: CONSTRUCTOR CHECK");
    }

    @NonNull
    @Override
    public WeatherForecastRecycler.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_recyclerview, parent, false);
        ForecastViewHolder forecastViewHolder = new ForecastViewHolder(view, WeatherForecastRecycler.this);
        Log.d(TAG, "onCreateViewHolder: Inside OnCreateVIEW HOLDER");
        return forecastViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.textViewRHumidity.setText("TEST");
        getWeathers(url);
        Log.d(TAG, "onBindViewHolder: ONBINDVIEWHOLDER");
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }


    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewforecast;
        TextView textViewRDate,textViewRTemp,textViewRMaxTemp,textViewRMinTemp,textViewRHumidity,textViewRClouds;
        public ForecastViewHolder(@NonNull View itemView, WeatherForecastRecycler adapter) {
            super(itemView);
            Log.d(TAG, "ForecastViewHolder: INSIDE FORECASTVIEWHOLDER CONS");
            imageViewforecast = itemView.findViewById(R.id.imageViewweather);
            textViewRDate = itemView.findViewById(R.id.textViewTemprature);
            textViewRTemp = itemView.findViewById(R.id.textViewTempratureMax);
            textViewRMaxTemp = itemView.findViewById(R.id.textViewTempratureMin);
            textViewRMinTemp = itemView.findViewById(R.id.textViewCloudiness);
            textViewRHumidity = itemView.findViewById(R.id.textViewDescription);
            textViewRClouds = itemView.findViewById(R.id.textViewHumidity);

        }
    }
    void getWeathers(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: " + request.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: INSIDE RESPONSE");
                if (response.isSuccessful()) {
                    try {
                        JSONObject weatherJsonObject = new JSONObject(response.body().string());
                        JSONArray forcast = weatherJsonObject.getJSONArray("list");
                        Log.d(TAG, "onResponse: JSON FORCAST");
                        for(int i = 0;i<forcast.length();i++){
                            JSONObject weekforecast = forcast.getJSONObject(i);
                            Weather weather = new Weather();

                            JSONObject main = weekforecast.getJSONObject("main");
                            weather.setTemprature(main.getString("temp"));
                            weather.setTempratureMax(main.getString("temp_max"));
                            weather.setTempratureMin(main.getString("temp_min"));
                            weather.setHumidity(main.getString("humidity"));

                            JSONArray weatherArray = weekforecast.getJSONArray("weather");
                            JSONObject weatherDescription = weatherArray.getJSONObject(0);
                            weather.setDescription(weatherDescription.getString("description"));
                            weather.setIcon(weatherDescription.getString("icon"));

                            JSONObject wind = weekforecast.getJSONObject("wind");
                            weather.setWindSpeed(wind.getString("speed"));
                            weather.setWindDegree(wind.getString("deg"));

                            JSONObject clouds = weekforecast.getJSONObject("clouds");
                            weather.setCloudiness(clouds.getString("all"));

                            weathers.add(weather);
                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                textViewTemprature.setText(weather.temprature + "F");
//                                textViewTempratureMax.setText(weather.tempratureMax + "F");
//                                textViewTempratureMin.setText(weather.tempratureMin + "F");
//                                textViewDescription.setText(weather.description);
//                                textViewHumidity.setText(weather.humidity + "%");
//                                textViewWindSpeed.setText(weather.windSpeed + " miles/hr ");
//                                textViewWindDegree.setText(weather.windDegree + " degrees ");
//                                textViewCloudiness.setText(weather.cloudiness + "%");
//
//
//                                String imgUrl = "https://openweathermap.org/img/wn/" + weather.icon +"@2x.png";
//                                Log.d(TAG, "onCreateView: " + imgUrl);
//                                Picasso.get().load(imgUrl).into(imageViewweather);
//                            }
//                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "out array: "+ weathers);
            }
        });
    }
}

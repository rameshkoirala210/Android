package com.example.hw05;

import android.widget.TextView;

public class Weather {
    String temprature,tempratureMax,tempratureMin,description,humidity,windSpeed, windDegree, cloudiness, icon, date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getTempratureMax() {
        return tempratureMax;
    }

    public void setTempratureMax(String tempratureMax) {
        this.tempratureMax = tempratureMax;
    }

    public String getTempratureMin() {
        return tempratureMin;
    }

    public void setTempratureMin(String tempratureMin) {
        this.tempratureMin = tempratureMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(String windDegree) {
        this.windDegree = windDegree;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temprature='" + temprature + '\'' +
                ", tempratureMax='" + tempratureMax + '\'' +
                ", tempratureMin='" + tempratureMin + '\'' +
                ", description='" + description + '\'' +
                ", humidity='" + humidity + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDegree='" + windDegree + '\'' +
                ", cloudiness='" + cloudiness + '\'' +
                ", icon='" + icon + '\'' +
                ", Date='" + date + '\'' +
                '}';
    }
}

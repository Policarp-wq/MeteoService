package com.policarp.meteoservice;

import android.content.Context;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    public double Temperature;
    public boolean IsDay;
    public String Condition;
    public String City;
    private String imageCode;
    public Weather(JSONObject weatherJSON) {
        JSONObject current = null;
        try {
            City = weatherJSON.getJSONObject("location").getString("name");
            current = weatherJSON.getJSONObject("current");
            Temperature = current.getDouble("temp_c");
            IsDay = current.getInt("is_day") == 1 ? true : false;
            JSONObject condition = current.getJSONObject("condition");
            Condition = condition.getString("text");
            String icon = condition.getString("icon");
            imageCode = icon.substring(icon.length() - 7, icon.length() - 4);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    public String getImageName(){
        return (IsDay ? "day" : "night") + imageCode;
    }
}

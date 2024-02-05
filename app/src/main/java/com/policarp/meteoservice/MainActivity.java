package com.policarp.meteoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.policarp.meteoservice.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("Info");
            Log.d("RESULT", str);
            JSONObject request = null;
            try {
                request = new JSONObject(str);
                Weather weather = new Weather(request);
                binding.cityName.setText(weather.City);
                binding.condition.setText(weather.Condition);
                binding.temp.setText(Double.toString(weather.Temperature));
                String name = weather.getImageName();
                binding.weatherImage.setImageResource(context.getResources().
                        getIdentifier(name, "drawable", context.getPackageName()));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver, new IntentFilter("MeteoService"), RECEIVER_EXPORTED);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(this, MeteoService.class);
        startService(intent);
        //stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MeteoService.class);
        stopService(intent);
    }
}
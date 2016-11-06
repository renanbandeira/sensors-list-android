package com.example.ipdi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SensorManager mSensorManager;
    List<Sensor> sensorList;
    ListView mSensorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);
        mSensorList = (ListView) findViewById(R.id.sensors_list);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorList.setOnItemClickListener(this);
        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sensorNames = new ArrayList<>();
        for(Sensor sensor : sensorList) {
            sensorNames.add(sensor.getName());
        }
        mSensorList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensorNames));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("sensor", sensorList.get(position).getType());
        startActivity(intent);
    }
}

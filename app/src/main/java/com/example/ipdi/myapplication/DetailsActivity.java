package com.example.ipdi.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by great on 13/10/2016.
 */
public class DetailsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    TextView mSensorStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorStatus = (TextView) findViewById(R.id.sensor_status);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (getIntent().hasExtra("sensor")) {
            mSensor = mSensorManager.getDefaultSensor(getIntent().getIntExtra("sensor", 0));
            if (mSensor != null) {
                getSupportActionBar().setTitle(mSensor.getName());
            }
        } else {
            Toast.makeText(this, "Erro ao receber tipo de sensor!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensor == null) return;
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensor == null) return;
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] eventData = event.values;
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for(Float value : eventData) {
            builder.append("Valor " + i + ": ");
            builder.append(value);
            builder.append("\n");
            i++;
        }
        mSensorStatus.setText(builder.toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        mSensorStatus.setText(mSensorStatus.getText().toString() + "\n Acurácia: " + accuracy);
    }
}

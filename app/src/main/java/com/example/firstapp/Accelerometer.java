package com.example.firstapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private float preValue[];
    private static final int SHAKE_THRESHOLD = 600;
    private TextView textX, textY, textZ;
    private TextView orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        textX = (TextView)findViewById(R.id.xvalue);
        textY = (TextView)findViewById(R.id.yvalue);
        textZ= (TextView)findViewById(R.id.zvalue);
        preValue= new float[3];
        orientation= (TextView) findViewById(R.id.ori);

    }
    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

   @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float xChange = preValue[0]-event.values[0];
                float yChange = preValue[1]-event.values[1];
                float zChange = preValue[2] -event.values[2];
                preValue[0]= event.values[0];
                preValue[1]= event.values[1];
                preValue[2]= event.values[2];

            long curTime = System.currentTimeMillis();

            textX.setText("X-value: "+ event.values[0]);

            textY.setText("Y-value: " + event.values[1]);

            textZ.setText("Z-value: " + event.values[2]);

            if(xChange>1){
                orientation.setText("Right");

            }
            if(xChange<1){
                orientation.setText("Left");
            }

            }
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

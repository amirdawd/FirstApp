package com.example.firstapp;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private float x, y,z;
    private static final int SHAKE_THRESHOLD = 600;
    private TextView textX, textY, textZ, orientation;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Vibrator vibrator;
    private android.support.constraint.ConstraintLayout layout;
    private MediaPlayer player;



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
        orientation= (TextView)findViewById(R.id.ori);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.parent);
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
                x= event.values[0];
                y= event.values[1];
                z = event.values[2];
                if(x>1){
                    orientation.setText("Right");

                }
                if(x<-1){
                    orientation.setText("Left");
                }
                if(x>-1 && x<1){
                    orientation.setText("Still");
                }
                setText(event);
                shakeListner();



        }
        }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setText(SensorEvent event){
        textX.setText("X-value: "+ Math.round(event.values[0]));

        textY.setText("Y-value: " +Math.round(event.values[0]));

        textZ.setText("Z-value: " + Math.round(event.values[0]));
    }
    public void play(){
        if(player==null){
            player= MediaPlayer.create(this,R.raw.beep);
        }
        player.start();
    }

    public void pause(){
        if(player!= null){
            player.stop();
        }
    }
    public void shakeListner() {
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if(mAccel>11){
            vibrator.vibrate(300);
            layout.setBackgroundColor(Color.RED);
        }
        else{
            layout.setBackgroundColor(Color.WHITE);
        }
    }
}

package com.example.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCompass();
            }
        });

        button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAccelerometer();
            }
        });
    }

    public void openCompass(){
        Intent intent = new Intent(this, Compass.class);
        startActivity(intent);
    }
    public void openAccelerometer(){
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }
}

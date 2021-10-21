package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.record.Recorder;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    ImageButton back_to_main;
    ImageButton start;
    ImageButton pause;

    File file;
    Recorder r = new Recorder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        //start button
        start = (ImageButton)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.setRecording(true);
                        file = r.startRecord();

                    }
                });
                RecordActivity.this.finish();
            }
        });



        //pause button
        pause = (ImageButton)findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(file.exists()){
                    try {
                        r.playRecord(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                RecordActivity.this.finish();
            }
        });

        //back to main page
        back_to_main = (ImageButton)findViewById(R.id.record_back_to_main);
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordActivity.this.finish();
            }
        });

    }

    protected void onDestroy(){
        super.onDestroy();
        r.setRecording(false);
        r.releaseAudioTrack();
    }
}
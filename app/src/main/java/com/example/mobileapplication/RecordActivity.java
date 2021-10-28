package com.example.mobileapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mobileapplication.oss.CloudStorageManager;
import com.example.mobileapplication.record.Recorder;
import com.example.mobileapplication.shake.ShakeManager;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    ImageButton back_to_main;
    ImageButton start_or_pause, pause, play;
    TextView text;
    Button mRecordUploadBtn;
    File file;
    Recorder r = new Recorder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        text = findViewById(R.id.msg);

        //start button
        start_or_pause = (ImageButton) findViewById(R.id.start);
        start_or_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("Start Recording...");
                start_or_pause.setImageResource(R.drawable.ic_pause);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.transfer();
                        if (r.getRecording()) file = r.startRecord();
                        text.setText("Finish Recording");
                        start_or_pause.setImageResource(R.drawable.ic_record);
                    }
                }).start();
            }
        });

        checkRecordPermission();

        //play button
        play = (ImageButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file.exists()) {
                    try {
                        r.playRecord(file);
                        text.setText("Playing Audio");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        //back to main page
        back_to_main = (ImageButton) findViewById(R.id.record_back_to_main);
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordActivity.this.finish();
            }
        });

        mRecordUploadBtn = findViewById(R.id.record_upload_btn);
        mRecordUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null && file.exists()) {
                    CloudStorageManager.getInstance().uploadFile("testUserName", file,
                            new CloudStorageManager.UploadCallback<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                }

                                @Override
                                public void onFail(Exception exception) {

                                }
                            });
                }
            }
        });

    }



    private void checkRecordPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        r.setRecording(false);
        r.releaseAudioTrack();
    }
}
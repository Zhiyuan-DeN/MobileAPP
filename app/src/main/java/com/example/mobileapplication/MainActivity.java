package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button profile_edt, new_recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* create a new voice recording */
        new_recording = findViewById(R.id.new_recording);
        new_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RecordActivity.class);
                startActivity(i);
            }
        });

        /* jump to profile page */
        profile_edt = findViewById(R.id.jump_to_profile);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        profile_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                if (userName != null) {
                    i.putExtra("userName", userName);
                }
                startActivity(i);
            }
        });
    }
}
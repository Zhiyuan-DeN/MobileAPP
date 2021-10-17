package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //jump to edit page
        Button profileEditButton = findViewById(R.id.edit_profile);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileEditActivity.class);
                if (userName != null) {
                    i.putExtra("userName", userName);
                }
                startActivity(i);

            }
        });

        //back to main page
        Button backToMain = findViewById(R.id.back_button);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}

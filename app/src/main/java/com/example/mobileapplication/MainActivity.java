package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;

public class MainActivity extends AppCompatActivity {

    public static User globalUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button new_record = findViewById(R.id.new_recording);
        new_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RecordActivity.class);
                startActivity(i);

            }
        });




        //jump to profile page
        Button profileEditButton = findViewById(R.id.jump_to_profile);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);

                if (userName != null) {
                    i.putExtra("userName", userName);

                    DatabaseModel.getInstance().getUserInfo(userName, new DatabaseModel.RequestResponse() {
                        @Override
                        public void onSuccess(User user) {
                            Toast.makeText(getApplicationContext(), "Get Info Successful", Toast.LENGTH_SHORT).show();
                            globalUser = user;
                            startActivity(i);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getApplicationContext(), "Get Info Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView usr_name;
    String j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usr_name = (TextView) findViewById(R.id.usr_name);
        Intent i_name = getIntent();
        Bundle get_usr_name = i_name.getExtras();
        String user =(String) get_usr_name.get("userName");
        String edit = (String) get_usr_name.get("edit_name");
        if(user != null) {
            usr_name.setText(user);
            j = usr_name;
        } else {
            usr_name.setText(edit);
            j = usr_name;
        }

        String username = j;//这里写上你从前端得到的name
        User user = DatabaseModel.getInstance().getUserInfo(username,new DatabaseModel.RequestResponse() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Register Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //jump to edit page
        Button profileEditButton = findViewById(R.id.edit_profile_btn);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileEditActivity.class);
                if (userName != null) {
                    i.putExtra("userName", userName);
                }
                i.putExtra("description", usr_description.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("location", location.getText().toString());
                startActivity(i);

            }
        });

        //back to main page
        Button backToMain = findViewById(R.id.back_to_main_btn);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
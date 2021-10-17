package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;

public class LoginActivity extends AppCompatActivity{
    Button button;
    EditText username;
    EditText password;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerButton = (TextView) findViewById(R.id.registerButton);
        //registerButton.setPaintFlags(registerButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                DatabaseModel.getInstance().getUserProfile(username1, password1, new DatabaseModel.RequestResponse() {
                    @Override
                    public void onSuccess(User user) {
                        String ok = "Login Successful";
                        // Login Successful
                        Toast.makeText(LoginActivity.this,ok,Toast.LENGTH_SHORT).show();

                        // Turn to main page
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("userName", user.getDocument());
                        startActivity(i);
                    }

                    @Override
                    public void onError(Exception e) {
                        String fail = "Login Failed";
                        // Login Successful
                        Toast.makeText(LoginActivity.this,fail,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }


}
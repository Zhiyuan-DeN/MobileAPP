package com.example.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    EditText username;
    EditText password;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        TextView textView = (TextView) findViewById(R.id.registerButton);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        button.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
        public void onClick(View v) {
        String username1 = username.getText().toString();
        String password1 = password.getText().toString();
        String ok = "Login Successful";
        String fail = "Login Failed";
        // Login Successful
        Toast.makeText(LoginActivity.this,ok,Toast.LENGTH_SHORT).show();


        // Turn to main page
        /*
        // Intent i = new Intent(getApplicationContext(),MainActivity.class);
        // startActivity(i);
         */
    }
}
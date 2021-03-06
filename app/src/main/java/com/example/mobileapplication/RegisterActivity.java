package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;

public class RegisterActivity extends AppCompatActivity {
    Button button;
    ImageButton back_to_login;
    EditText username, password, email, phone_no;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        phone_no = (EditText) findViewById(R.id.phone_no);
        back_to_login = (ImageButton) findViewById(R.id.back_to_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_string = email.getText().toString().trim();
                String password_string = password.getText().toString();
                String userName_String = username.getText().toString();
                if (email_string.length() > 0 && password_string.length() >= 8) {
                    DatabaseModel.getInstance().requestRegister(new User(
                            "test",
                            email_string,
                            password_string,
                            "123",
                            userName_String,
                            userName_String,
                            "123"
                    ), new DatabaseModel.RequestResponse() {
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

                } else if (password_string.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }

}
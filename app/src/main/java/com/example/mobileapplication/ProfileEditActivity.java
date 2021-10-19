package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileEditActivity extends AppCompatActivity {
    Spinner select_zodiac;
    EditText usr_description, email, location, phone;
    Button update, reset;
    String edit_name, edit_description, edit_email, edit_location, edit_phone;
    TextView unchangeable_usr_name;
    String j;

    Spinner select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Intent intent = getIntent();
        String oldUserName = intent.getStringExtra("userName");

        unchangeable_usr_name = (TextView) findViewById(R.id.unchangeable_usr_name);
        usr_description = (EditText) findViewById(R.id.edit_descroption);
        email = (EditText) findViewById(R.id.edit_email);
        location = (EditText) findViewById(R.id.edit_location);
        phone = (EditText) findViewById(R.id.edit_phone);
        update = findViewById(R.id.profile_update_btn);

        unchangeable_usr_name.setText(oldUserName);

        //update profile
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile();
                if (valid_input() == true) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                    intent.putExtra("edit_name", edit_name);
                    intent.putExtra("edit_description", edit_description);
                    intent.putExtra("edit_email", edit_email);
                    intent.putExtra("edit_location", edit_location);
                    intent.putExtra("edit_phone", edit_phone);
                    startActivity(intent);
                }
            }
        });

        //clear all
        reset = findViewById(R.id.profile_reset_btn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                usr_name.setText("");
                usr_description.setText("");
                email.setText("");
                location.setText("");
                phone.setText("");
            }
        });

        //back to profile
        ImageButton back_to_profile = (ImageButton)findViewById(R.id.back_to_profile);
        back_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void update_profile() {
//        edit_name = usr_name.getText().toString();
        edit_description = usr_description.getText().toString();
        edit_email = email.getText().toString();
        edit_location = location.getText().toString();
        edit_phone = phone.getText().toString();
    }

    public boolean valid_input() {
//        if (edit_name.equals("")) {
//            usr_name.setError("Please enter user name.");
//            return false;
//        }
        if (edit_description.equals("")) {
            usr_description.setError("Please enter descroption.");
            return false;
        } else if (edit_email.equals("")) {
            email.setError("Please enter your email.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edit_email).matches()) {
            email.setError("Please use a valid email address.");
            return false;
        } else if (edit_location.equals("")) {
            location.setError("Please enter a location.");
            return false;
        } else if (edit_phone.equals("")) {
            phone.setError("Please enter a phone number.");
            return false;
        } else {
            Toast.makeText(this,"Update Successfully!",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
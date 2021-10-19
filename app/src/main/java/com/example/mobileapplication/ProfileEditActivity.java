package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;

public class ProfileEditActivity extends AppCompatActivity {
    Button update, reset;
    EditText usr_name, usr_description, email, location, phone;
    Spinner select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //Intent intent = getIntent();
        //String oldUserName = intent.getStringExtra("userName");
        
        Intent i = getIntent();
        Bundle b = i.getExtras();

        String user_prof =(String) b.get("userName");
        if(user_prof != null) {
            usr_name.setText(user_prof);
        }
        String edit_prof = (String) b.get("description");
        if(edit_prof != null) {
            usr_description.setText(edit_prof);
        }
        String email_prof = (String) b.get("email");
        if(edit_prof != null) {
            email.setText(email_prof);
        }
        String location_prof = (String) b.get("location");
        if(location_prof != null) {
            location.setText(location_prof);
        }

//        select = findViewById(R.id.edit_zodiac);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.zodiac_array));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        select.setAdapter(adapter);


        usr_name = (EditText) findViewById(R.id.edit_usr_name);
        usr_description = (EditText) findViewById(R.id.edit_descroption);
        email = (EditText) findViewById(R.id.edit_email);
        location = (EditText) findViewById(R.id.edit_location);
        phone = (EditText) findViewById(R.id.edit_phone);
        update = findViewById(R.id.profile_update_btn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseModel.getInstance().updateUserProfile(oldUserName, usr_name.getText().toString(),
                        update.getText().toString(), email.getText().toString(),
                        location.getText().toString(), new DatabaseModel.RequestResponse() {
                            @Override
                            public void onSuccess(User user) {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }
        });


        //clear all
        reset = findViewById(R.id.profile_reset_btn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clearing out all the values
                usr_name.setText("");
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
}
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

    Spinner select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Intent intent = getIntent();
        String oldUserName = intent.getStringExtra("userName");

//        select = findViewById(R.id.edit_zodiac);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.zodiac_array));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        select.setAdapter(adapter);

        ImageButton back_to_profile = (ImageButton)findViewById(R.id.back_to_profile);
        Button update_profile_button = findViewById(R.id.profile_update_btn);

        EditText userName = findViewById(R.id.edit_usr_name);
        EditText edit_descroption = findViewById(R.id.edit_descroption);
        EditText email = findViewById(R.id.edit_email);
        EditText location = findViewById(R.id.edit_location);

        update_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseModel.getInstance().updateUserProfile(oldUserName, userName.getText().toString(),
                        edit_descroption.getText().toString(), email.getText().toString(),
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

        back_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}

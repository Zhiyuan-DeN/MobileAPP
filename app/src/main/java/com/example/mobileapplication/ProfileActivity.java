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
    Button profile_edit, back_to_main;
    TextView usr_name, usr_description, email, location, phone;
    String j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usr_name = (TextView) findViewById(R.id.usr_name);
        Intent i_name = getIntent();
        Bundle get_usr_name = i_name.getExtras();
        String init_user =(String) get_usr_name.get("userName");
        String edit = (String) get_usr_name.get("unable_edt_name");
        if(init_user != null) {
            usr_name.setText(init_user);
        } else {
            usr_name.setText(edit);
        }
        usr_description = (TextView) findViewById(R.id.usr_description);
        email = (TextView) findViewById(R.id.email);
        location = (TextView) findViewById(R.id.location);
        phone = (TextView) findViewById(R.id.phone);

//        String username = j;//这里写上你从前端得到的name
//        User user = DatabaseModel.getInstance().getUserInfo(username,new DatabaseModel.RequestResponse() {
//            @Override
//            public void onSuccess(User user) {
//                Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(getApplicationContext(), "Register Failed",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });



//        Intent i_name = getIntent();
//        String get_usr_name = i_name.getStringExtra("unable_edt_name");
//        usr_name.setText(get_usr_name);

        Intent i_desc = getIntent();
        String get_desc = i_desc.getStringExtra("edit_description");
        usr_description.setText(get_desc);

        Intent i_email = getIntent();
        String get_email = i_email.getStringExtra("edit_email");
        email.setText(get_email);

        Intent i_loc = getIntent();
        String get_loc = i_loc.getStringExtra("edit_location");
        location.setText(get_loc);

        Intent i_phone = getIntent();
        String get_phone = i_phone.getStringExtra("edit_phone");
        phone.setText(get_phone);


        /* jump to edit page */
        profile_edit = findViewById(R.id.edit_profile_btn);
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        profile_edit.setOnClickListener(new View.OnClickListener() {
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
        back_to_main = findViewById(R.id.back_to_main_btn);
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
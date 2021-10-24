package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileapplication.database.User;
import com.example.mobileapplication.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    Button profile_edit, back_to_main;
    TextView usr_name, usr_description, email, location, phone;

    @Override
    public void onResume() {
        super.onResume();
        //getActivity().finish();
        //startActivity(getActivity().getIntent());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        User user = MainActivity.globalUser;
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);

        usr_name = (TextView)binding.usrName;
        Intent i_name = getActivity().getIntent();
        Bundle get_usr_name = i_name.getExtras();
        String init_user =(String) get_usr_name.get("userName");
        String edit = (String) get_usr_name.get("unable_edt_name");
        if(init_user != null) {
            usr_name.setText(init_user);
        } else {
            usr_name.setText(edit);
        }

        usr_description = (TextView) binding.usrDescription;
        email = (TextView) binding.email;
        location = (TextView) binding.location;
        phone = (TextView)binding.phone;
        if (user != null) {
            String get_desc = user.getDescription();
            usr_description.setText(get_desc);

            String get_email = user.getEmail();
            email.setText(get_email);

            String get_loc = user.getLocation();
            location.setText(get_loc);

            String get_phone = user.getPhoneNum();
            phone.setText(get_phone);
        } else {
            Intent i_desc = getActivity().getIntent();
            String get_desc = i_desc.getStringExtra("edit_description");
            usr_description.setText(get_desc);

            Intent i_email = getActivity().getIntent();
            String get_email = i_email.getStringExtra("edit_email");
            email.setText(get_email);

            Intent i_loc = getActivity().getIntent();
            String get_loc = i_loc.getStringExtra("edit_location");
            location.setText(get_loc);

            Intent i_phone = getActivity().getIntent();
            String get_phone = i_phone.getStringExtra("edit_phone");
            phone.setText(get_phone);
        }

        /* jump to edit page */
        profile_edit = binding.editProfileBtn;
        Intent intent = getActivity().getIntent();
        final String userName = intent.getStringExtra("userName");
        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),ProfileEditActivity.class);
                if (userName != null) {
                    i.putExtra("userName", userName);
                }
                startActivity(i);

            }
        });

        back_to_main = binding.backToMainBtn;
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment.this.onPause();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        Log.d("Tag", "ProfileFragment.onDestroyView() has been called.");
        super.onDestroyView();
        binding = null;
    }
}
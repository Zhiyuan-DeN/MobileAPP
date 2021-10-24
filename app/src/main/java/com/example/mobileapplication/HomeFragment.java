package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileapplication.R;
import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;
import com.example.mobileapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public static User globalUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button new_record = binding.newRecording;
        new_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),RecordActivity.class);
                startActivity(i);

            }
        });

        Button profileEditButton = binding.jumpToProfile;
        Intent intent = getActivity().getIntent();
        final String userName = intent.getStringExtra("userName");
        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),ProfileActivity.class);

                if (userName != null) {
                    i.putExtra("userName", userName);

                    DatabaseModel.getInstance().getUserInfo(userName, new DatabaseModel.RequestResponse() {
                        @Override
                        public void onSuccess(User user) {
                            Toast.makeText(getActivity().getApplicationContext(), "Get Info Successful", Toast.LENGTH_SHORT).show();
                            globalUser = user;
                            startActivity(i);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Get Info Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
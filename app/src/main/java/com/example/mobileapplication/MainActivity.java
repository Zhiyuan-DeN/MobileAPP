package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobileapplication.databinding.ActivityMainBinding;
import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static User globalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_record, R.id.navigation_profile)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        // NavController navController = navHostFragment.getNavController();

        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");

        DatabaseModel.getInstance().getUserInfo(userName, new DatabaseModel.RequestResponse() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(getApplicationContext(), "Get Info Successful", Toast.LENGTH_SHORT).show();
                globalUser = user;
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Get Info Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
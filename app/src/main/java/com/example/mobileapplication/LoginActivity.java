package com.example.mobileapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DatabaseModel;
import com.example.mobileapplication.database.User;
import com.example.mobileapplication.oss.CloudStorageManager;
import com.example.mobileapplication.utils.PathUtils;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class LoginActivity extends AppCompatActivity{
    Button button;
    EditText username;
    EditText password;
    TextView registerButton;
    public static Map<File, String> postMap;

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
                        CloudStorageManager cloudStorageManager = new CloudStorageManager();
                        cloudStorageManager.downloadFile(new CloudStorageManager.UploadCallback<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                File[] directoryList = new File(PathUtils.getDownloadPath()).listFiles();

                                postMap = new TreeMap<File, String>(new Comparator<File>() {
                                    @Override
                                    public int compare(File o1, File o2) {
                                        return -o1.getName().compareTo(o2.getName());
                                    }
                                });

                                for (File directory:directoryList) {
                                    for (File file:directory.listFiles()) {
                                        postMap.put(file, directory.getName());
                                    }
                                }

                                // Turn to main page
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                i.putExtra("userName", user.getDocument());
                                startActivityForResult(i,1);
                            }

                            @Override
                            public void onFail(Exception exception) {
                                String fail = "Load Page Failed";
                                // Login Successful
                                Toast.makeText(LoginActivity.this,fail,Toast.LENGTH_SHORT).show();
                            }
                        });

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

    public Button getButton() {
        return button;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Tag", "Returned result" + resultCode + resultCode);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                CloudStorageManager cloudStorageManager = new CloudStorageManager();
                cloudStorageManager.downloadFile(new CloudStorageManager.UploadCallback<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        File[] directoryList = new File(PathUtils.getDownloadPath()).listFiles();

                        postMap = new TreeMap<File, String>(new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return -o1.getName().compareTo(o2.getName());
                            }
                        });

                        for (File directory : directoryList) {
                            for (File file : directory.listFiles()) {
                                postMap.put(file, directory.getName());
                            }
                        }

                        // Turn to main page
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("userName", MainActivity.globalUser.getDocument());
                        startActivityForResult(i, 1);
                    }

                    @Override
                    public void onFail(Exception exception) {
                        String fail = "Load Page Failed";
                        // Login Successful
                        Toast.makeText(LoginActivity.this,fail,Toast.LENGTH_SHORT).show();
                    }

                });

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult


}
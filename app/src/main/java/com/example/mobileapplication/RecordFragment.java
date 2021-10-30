package com.example.mobileapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mobileapplication.database.User;
import com.example.mobileapplication.databinding.FragmentRecordBinding;
import com.example.mobileapplication.oss.CloudStorageManager;
import com.example.mobileapplication.record.Recorder;
import com.example.mobileapplication.shake.ShakeManager;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {

    ImageButton back_to_main;
    ImageButton start_or_pause, pause, play;
    TextView text;
    User User = MainActivity.globalUser;
    private ShakeManager shake;
    File file;
    Recorder r = new Recorder();
    private FragmentRecordBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // setContentView(R.layout.activity_recording);
        text = binding.msg;
        start_or_pause = (ImageButton)binding.start;
        start_or_pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                text.setText("Start Recording...");
                start_or_pause.setImageResource(R.drawable.ic_pause);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.transfer();
                        if(r.getRecording()) file = r.startRecord();
                        text.setText("Finish Recording");
                        start_or_pause.setImageResource(R.drawable.ic_record);
                    }
                }).start();
            }
        });

        //shake phone
        shake = new ShakeManager(getContext());
        shake.setShakeListener(new ShakeManager.ShakeListener() {
            @Override
            public void onShake() {

                text.setText("Start Recording...");
                start_or_pause.setImageResource(R.drawable.ic_pause);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.transfer();
                        if (r.getRecording()) file = r.startRecord();
                        text.setText("Finish Recording");
                        start_or_pause.setImageResource(R.drawable.ic_record);
                    }
                }).start();
            }
        });

        checkRecordPermission();

        play = (ImageButton)binding.play;
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(file.exists()){
                    try {
                        r.playRecord(file);
                        text.setText("Playing Audio");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        binding.recordUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null && file.exists()) {
                    CloudStorageManager.getInstance().uploadFile(User.getUserName(), file,
                            new CloudStorageManager.UploadCallback<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "Upload Successful",
                                            Toast.LENGTH_SHORT).show();

                                    Intent returnIntent = new Intent();
                                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                                    getActivity().finish();

                                    /*
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    HomeFragment homeFragment = (HomeFragment)fm.findFragmentById(R.id.navigation_home);
                                    if(homeFragment == null) {
                                        Log.d("Tag", "homeFragment is null");

                                    }else {
                                        homeFragment.addPost(R.drawable.default_avatar, User.getUserName(), file);
                                    }

                                    //getActivity().recreate();

                                    Post new_post = new Post(R.drawable.default_avatar, User.getUserName(), file);
                                    HomeFragment fragment = new HomeFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("value",new_post);
                                    fragment.setArguments(bundle);
                                    getParentFragment().getChildFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_home, fragment, "Tag")
                                            .addToBackStack("Tag").commit();

                                     */
                                }

                                @Override
                                public void onFail(Exception exception) {
                                    Toast.makeText(getContext(), "Upload Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        return root;
    }

    private void checkRecordPermission() {

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }

    }

    @Override
    public void onDestroyView() {
        Log.d("Tag", "RecordFragment.onDestroyView() has been called.");
        shake.stop();
        super.onDestroyView();
        r.setRecording(false);
        r.releaseAudioTrack();
        binding = null;
    }
}

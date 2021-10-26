package com.example.mobileapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileapplication.R;
import com.example.mobileapplication.databinding.FragmentRecordBinding;
import com.example.mobileapplication.record.Recorder;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {

    ImageButton back_to_main;
    ImageButton start_or_pause, pause, play;
    TextView text;

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
        super.onDestroyView();
        r.setRecording(false);
        r.releaseAudioTrack();
        binding = null;
    }
}
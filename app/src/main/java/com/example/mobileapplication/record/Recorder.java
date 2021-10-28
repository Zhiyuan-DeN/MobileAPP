package com.example.mobileapplication.record;


import android.media.AudioRecord;
import android.media.AudioTrack;

import com.example.mobileapplication.utils.PathUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Recorder {
    private boolean recording = false;
    private AudioTrack audioTrack;

    public void setRecording(boolean b) {
        recording = b;
    }

    public void transfer() {
        if (recording) {
            recording = false;
        } else recording = true;
    }

    public boolean getRecording() {
        return recording;
    }

    public void releaseAudioTrack() {
        if (audioTrack != null) audioTrack.release();
    }

    public void playRecord(File file) throws IOException {
        int i = 0;
        int j = 0;

        int shortLength = Short.SIZE / Byte.SIZE;
        int bufferSize = (int) file.length() / shortLength;
        short[] data = new short[bufferSize];
        InputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        while (dataInputStream.available() > 0) {
            data[j] = dataInputStream.readShort();
            j++;

        }
        dataInputStream.close();

        i = 5000;

        int max=4,min=1;
        long randomNum = System.currentTimeMillis();
        int ran3 = (int) (randomNum%(max-min)+min);
        if (ran3 == 1) {
            i = 6050;
        }
        if (ran3 == 2) {
            i = 8500;
        }
        if (ran3 == 3) {
            i = 16000;
        }

        audioTrack = new AudioTrack(3, i, 2, 2, bufferSize, 1);
        audioTrack.play();
        audioTrack.write(data, 0, bufferSize);


    }

    private String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public File startRecord() {
        File recordFile = PathUtils.getFileWithCreate(PathUtils.getRecordPath(),
                getTimeStamp() + ".pcm");
        try {
            recordFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(recordFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bufferOut = new BufferedOutputStream(out);
        DataOutputStream dataOut = new DataOutputStream(bufferOut);

        int bufferMin = AudioRecord.getMinBufferSize(11025, 2, 2);
        short[] s = new short[bufferMin];
        AudioRecord ar = new AudioRecord(1, 11025, 2, 2, bufferMin);
        ar.startRecording();

        while (recording) {
            int count = ar.read(s, 0, bufferMin);
            for (int i = 0; i < count; i++) {
                try {
                    dataOut.writeShort(s[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //booleanValue
        if (!recording) {
            ar.stop();
            try {
                dataOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recordFile;


    }
}

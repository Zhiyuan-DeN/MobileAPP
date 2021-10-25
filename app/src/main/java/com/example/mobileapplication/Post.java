package com.example.mobileapplication;

import android.media.AudioTrack;

public class Post {

    private int profileImage; // ProfileImage to store the resource id
    private String userName;
    private AudioTrack audioTrack; // audio

    public Post(int profileImage, String userName, AudioTrack audioTrack) {
        this.profileImage = profileImage;
        this.userName = userName;
        this.audioTrack = audioTrack;
    }

    public int getProfileImage() {
        return profileImage;
    }


    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AudioTrack getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(AudioTrack audioTrack) {
        this.audioTrack = audioTrack;
    }
}

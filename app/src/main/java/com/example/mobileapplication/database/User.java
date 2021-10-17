package com.example.mobileapplication.database;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String mDescription;
    private String mEmail;
    private String mPassWord;
    private String mPhoneNum;
    private String mDocument;
    private String mUserName;
    private String mLocation;

    public User() {

    }

    public User(String description, String email, String passWord, String phoneNum,
                String userId, String userName, String location) {
        this.mDescription = description;
        this.mEmail = email;
        this.mPassWord = passWord;
        this.mPhoneNum = phoneNum;
        this.mDocument = userId;
        this.mUserName = userName;
        this.mLocation = location;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public String getDocument() {
        return mDocument;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPassWord(String mPassWord) {
        this.mPassWord = mPassWord;
    }

    public void setPhoneNum(String mPhoneNum) {
        this.mPhoneNum = mPhoneNum;
    }

    public void setDocument(String mUserId) {
        this.mDocument = mUserId;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("Description", this.mDescription);
        user.put("Email", this.mEmail);
        user.put("Password", this.mPassWord);
        user.put("PhoneNum", this.mPhoneNum);
        user.put("UserID", this.mDocument);
        user.put("UserName", this.mUserName);
        user.put("location", this.mLocation);
        return user;
    }

}

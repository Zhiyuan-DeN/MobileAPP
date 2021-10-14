package com.example.mobileapplication.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

public class DatabaseModel {
    private final String TAG = "DatabaseModel";

    private static DatabaseModel instance;
    private FirebaseFirestore db;

    private DatabaseModel() {
        db = FirebaseFirestore.getInstance();
    }

    public static DatabaseModel getInstance() {
        if (instance == null) {
            instance = new DatabaseModel();
        }
        return instance;
    }

    public void requestLogin(String userName, String password, RequestResponse requestResponse) {
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                if (Objects.equals(data.get("UserName"), userName) && Objects.equals(data.get(
                                        "Password"), password)) {
                                    requestResponse.onSuccess(new User(
                                            data.get("Description").toString(),
                                            data.get("Email").toString(),
                                            data.get("Password").toString(),
                                            data.get("PhoneNum").toString(),
                                            data.get("UserID").toString(),
                                            data.get("UserName").toString(),
                                            data.get("location").toString()
                                    ));
                                    return;
                                }
                            }
                        }
                        requestResponse.onError(new RuntimeException("login error"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        requestResponse.onError(e);
                    }
                });
    }

    public void requestRegister(User user, RequestResponse requestResponse) {
        db.collection("Users")
                .add(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        requestResponse.onSuccess(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        requestResponse.onError(e);
                    }
                });
    }

    public interface RequestResponse {
        void onSuccess(User user);

        void onError(Exception e);
    }
}

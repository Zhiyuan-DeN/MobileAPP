package com.example.mobileapplication.database;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseModel {
    private final String TAG = "DatabaseModel";
    User globalUser;

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

    public void getUserProfile(String userName, String password, RequestResponse requestResponse) {
        db.collection("Users")
                .document(userName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user == null) {
                            requestResponse.onError(new RuntimeException());
                        } else if (user.getUserName().equals(userName) && user.getPassWord().equals(password)) {
                            requestResponse.onSuccess(user);
                        } else {
                            requestResponse.onError(new RuntimeException());
                        }
                    }
                })
                /* .addOnSuccessListener(new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull DocumentSnapshot task) {
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
                         requestResponse.onError(new RuntimeException("cant match any one."));
                     }
                 })*/
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        requestResponse.onError(e);
                    }
                });
    }

    public void requestRegister(User user, RequestResponse requestResponse) {
        db.collection("Users")
                .document(user.getUserName())
                .set(user)
                //.add(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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

    public void updateUserProfile(User user,RequestResponse requestResponse) {
        db.collection("Users")
                .document(user.getUserName())
                .set(user)
                //.add(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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
    public void getUserInfo(String userName, RequestResponse requestResponse) {
        db.collection("Users")
                .document(userName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        globalUser = documentSnapshot.toObject(User.class);
                        if (globalUser == null) {
                            requestResponse.onError(new RuntimeException());
                        } else if (globalUser.getUserName().equals(userName)) {
                            requestResponse.onSuccess(globalUser);
                        } else {
                            requestResponse.onError(new RuntimeException());
                        }
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

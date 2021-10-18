package com.example.mobileapplication.database;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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

    public void updateUserProfile(String oldUserName, String userName, String description,
                                  String email,
                                  String location, RequestResponse requestResponse) {
        DocumentReference user = db.collection("Users")
                .document(oldUserName);
        HashMap<String, Object> map = new HashMap();
        map.put("userName", userName);
        map.put("description", description);
        map.put("email", email);
        map.put("location", location);
        user.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                requestResponse.onSuccess(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                requestResponse.onError(e);
            }
        });
    }

    public User getUserInfo(String userName, RequestResponse requestResponse) {
        User user = new User();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                if (Objects.equals(data.get("UserName"), userName)) {
                                    user.setDescription(data.get("Description").toString());
                                    user.setEmail(data.get("Email").toString());
                                    user.setPassWord(data.get("Password").toString());
                                    user.setPhoneNum(data.get("PhoneNum").toString());
                                    user.setUserId(data.get("UserID").toString());
                                    user.setUserName(data.get("UserName").toString());
                                    user.setLocation(data.get("location").toString());
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
        return user;
    }

    public interface RequestResponse {
        void onSuccess(User user);

        void onError(Exception e);
    }
}

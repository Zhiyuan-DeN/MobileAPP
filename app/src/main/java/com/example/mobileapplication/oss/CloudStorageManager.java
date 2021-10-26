package com.example.mobileapplication.oss;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class CloudStorageManager {
    private FirebaseStorage storage;
    private static CloudStorageManager instance;

    public static CloudStorageManager getInstance() {
        if (instance == null) {
            instance = new CloudStorageManager();
        }
        return instance;
    }

    private CloudStorageManager() {
        storage = FirebaseStorage.getInstance();
    }

    public void uploadFile(String userName, File uploadFile, @Nullable UploadCallback callback) {
        StorageReference storageRef = storage.getReference();
        // [START upload_file]
        Uri file = Uri.fromFile(uploadFile);
        StorageReference riversRef =
                storageRef.child("record/" + userName + "/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (callback != null) {
                    callback.onFail(exception);
                }
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (callback != null) {
                    callback.onSuccess();
                }
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public interface UploadCallback {
        void onSuccess();

        void onFail(Exception exception);
    }

}

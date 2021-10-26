package com.example.mobileapplication.oss;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileapplication.utils.PathUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
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

    public void downloadFile(@Nullable UploadCallback<FileDownloadTask.TaskSnapshot> callback) {
        // [START download_to_local_file]
        StorageReference storageRef = storage.getReference();
        StorageReference recordRef = storageRef.child("record/");
        File downloadFolder = new File(PathUtils.getDownloadPath());
        if (!downloadFolder.exists()) {
            downloadFolder.mkdir();
        }
        recordRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix : listResult.getPrefixes()) {
                    prefix.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference item : listResult.getItems()) {
                                File downloadFile =
                                        PathUtils.getFileWithCreate(PathUtils.getDownloadPath() + item.getParent().getName(), item.getName());
                                item.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        if (callback != null) {
                                            callback.onSuccess(taskSnapshot);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if (callback != null) {
                                            callback.onFail(e);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void uploadFile(String userName, File uploadFile, @Nullable UploadCallback<UploadTask.TaskSnapshot> callback) {
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
                    callback.onSuccess(taskSnapshot);
                }
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public interface UploadCallback<T> {
        void onSuccess(T t);

        void onFail(Exception exception);
    }

}

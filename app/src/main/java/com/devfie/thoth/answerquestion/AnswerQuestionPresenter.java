package com.devfie.thoth.answerquestion;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

/**
 * Created by mac-onur on 10.04.2017.
 */

public class AnswerQuestionPresenter implements AnswerQuestionContract.Presenter, PermissionListener {
    private final AnswerQuestionContract.View mView;

    public AnswerQuestionPresenter(AnswerQuestionContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void checkPremissions(Context context) {
        new TedPermission(context).setPermissionListener(this)
                .setDeniedMessage(R.string.denied_permission_message)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    @Override
    public void uploadPhoto(Uri photo) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("images/" + photo.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(photo);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mView.photoCantUploaded(exception.getMessage());
                // Handle unsuccessful uploads
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //    mView.uploadImageProgressChange(taskSnapshot.getTotalByteCount(), taskSnapshot.getBytesTransferred());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mView.photoUploaded(taskSnapshot.getDownloadUrl().toString());
            }
        });
    }


    @Override
    public void sendAnswer(String questionId, String photoUrl, String description, Float ratio, final Context context) {
        NetworkRequests.with(context).sendAsnwer(LocalDataManager.getInstance().getToken(), questionId, photoUrl, description, ratio, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                mView.answerSended();
            }

            @Override
            public void onError(String errorMsg) {
                mView.answerSendFailed(errorMsg);

            }

            @Override
            public void onServerError() {
                mView.answerSendFailed(context.getString(R.string.error_send_answer));
            }
        });
    }

    @Override
    public void pickPicture(Context mContext, FragmentManager manager) {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(mContext)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        mView.photoSelected(uri);
                    }
                })
                .create();

        tedBottomPicker.show(manager);
    }

    @Override
    public void onPermissionGranted() {
        mView.permissionsChecked();
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

    }
}

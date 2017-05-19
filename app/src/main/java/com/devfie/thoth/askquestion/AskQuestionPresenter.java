package com.devfie.thoth.askquestion;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import com.devfie.thoth.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;


import gun0912.tedbottompicker.TedBottomPicker;

/**
 * Created by mac-onur on 7.04.2017.
 */

public class AskQuestionPresenter implements AskQuestionContract.Presenter, PermissionListener {

    private final AskQuestionContract.View mQuestionView;

    public AskQuestionPresenter(AskQuestionContract.View mQuestionView) {
        this.mQuestionView = mQuestionView;
        mQuestionView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void takePicture() {

    }

    @Override
    public void pickPicture(Context mContext, FragmentManager manager) {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(mContext)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        mQuestionView.pictureSelected(uri);
                    }
                })
                .create();

        tedBottomPicker.show(manager);

    }




    @Override
    public void checkValues(String picturePath) {
        if (picturePath != null && !picturePath.isEmpty()) mQuestionView.nextStep();
    }

    @Override
    public void checkPermissions(Context context) {
        new TedPermission(context).setPermissionListener(this)
                .setDeniedMessage(R.string.denied_permission_message)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    @Override
    public void onPermissionGranted() {
        mQuestionView.permissionsCheckedSuccess();
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        mQuestionView.permissionsDenied();
    }
}

package com.devfie.thoth.askquestion;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;


/**
 * Created by mac-onur on 7.04.2017.
 */

public interface AskQuestionContract {
    interface View extends BaseView<Presenter> {
        void pictureSelected(Uri path);

        void questionCanShare();

        void permissionsCheckedSuccess();

        void permissionsDenied();

        void nextStep();
    }

    interface Presenter extends BasePresenter {
        void takePicture();

        void pickPicture(Context mContext, FragmentManager manager);

        void checkValues(String picturePath);

        void checkPermissions(Context context);
    }
}

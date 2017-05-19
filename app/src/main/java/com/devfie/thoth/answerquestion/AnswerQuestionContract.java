package com.devfie.thoth.answerquestion;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import com.devfie.thoth.base.BasePresenter;
import com.devfie.thoth.base.BaseView;

/**
 * Created by mac-onur on 10.04.2017.
 */

public interface AnswerQuestionContract {

    public interface View extends BaseView<Presenter> {
        void permissionsChecked();

        void photoUploaded(String url);

        void photoCantUploaded(String errorMsg);

        void answerSended();

        void answerSendFailed(String error);

        void photoSelected(Uri uri);
    }

    public interface Presenter extends BasePresenter {
        void checkPremissions(Context context);

        void uploadPhoto(Uri photo);

        void sendAnswer(String questionId, String photoUrl, String description, Float ratio, Context context);

        void pickPicture(Context mContext, FragmentManager manager);

    }
}

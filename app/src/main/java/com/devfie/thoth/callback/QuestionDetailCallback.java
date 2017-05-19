package com.devfie.thoth.callback;

import android.view.View;

import com.devfie.thoth.model.Question;

/**
 * Created by mac-onur on 10.04.2017.
 */

public interface QuestionDetailCallback {

    void onProfileClick();

    void onFollowClick();

    void onMoreClick(View view);

    void onAnswerQuestionClick();
}

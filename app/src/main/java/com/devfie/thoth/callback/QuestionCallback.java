package com.devfie.thoth.callback;

import android.view.View;
import android.widget.ImageButton;

import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;

/**
 * Created by mac-onur on 8.04.2017.
 */

public interface QuestionCallback {
    void onQuestionClick(Question question);

    void onFollowClick(Question question);

    void onMoreClick(Question question, View button);

    void onOwnerClick(Question question);

}

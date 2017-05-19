package com.devfie.thoth.callback;

import com.devfie.thoth.model.Question;

/**
 * Created by mac-onur on 17.04.2017.
 */

public interface QuestionFindedCallback {
    void onQuestionFind(Question question);
}

package com.devfie.thoth.callback;

import android.view.View;

import com.devfie.thoth.model.Answer;
import com.devfie.thoth.model.Question;

/**
 * Created by mac-onur on 8.04.2017.
 */

public interface AnswerCallback {

    void onAnswerUpVote(Answer answer);

    void onAnswerDownVote(Answer answer);

    void onAnswerMoreClick(View view, Answer answer);


}

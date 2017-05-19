package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 16.04.2017.
 */

public class AskQuestionResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

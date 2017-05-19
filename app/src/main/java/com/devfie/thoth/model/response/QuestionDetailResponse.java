package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 15.04.2017.
 */

public class QuestionDetailResponse extends BaseResponse {


    @Expose
    @SerializedName("response")
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

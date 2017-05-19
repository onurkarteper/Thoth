package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Answer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 14.04.2017.
 */

public class AnswerVoteResponse extends BaseResponse{

    @SerializedName("response")
    @Expose
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


}

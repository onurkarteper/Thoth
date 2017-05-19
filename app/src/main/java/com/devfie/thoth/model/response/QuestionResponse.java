package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public class QuestionResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

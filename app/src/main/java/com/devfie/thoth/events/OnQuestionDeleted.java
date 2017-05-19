package com.devfie.thoth.events;

/**
 * Created by mac-onur on 16.04.2017.
 */

public class OnQuestionDeleted {

    private String questionId;


    public OnQuestionDeleted(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

}

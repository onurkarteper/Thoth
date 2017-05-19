package com.devfie.thoth.events;

/**
 * Created by mac-onur on 16.04.2017.
 */

public class QuestionDetailCantLoad {
    String error;

    public QuestionDetailCantLoad(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

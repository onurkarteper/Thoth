package com.devfie.thoth.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class BaseResponse {
    @SerializedName("error")
    @Expose
    Boolean error;
    @SerializedName("msg")
    @Expose
    String message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

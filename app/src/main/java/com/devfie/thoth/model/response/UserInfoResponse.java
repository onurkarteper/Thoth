package com.devfie.thoth.model.response;

import com.devfie.thoth.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 16.04.2017.
 */

public class UserInfoResponse  extends BaseResponse{

    @SerializedName("response")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


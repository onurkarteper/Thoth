package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class User extends BaseObservable {

    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;

    @SerializedName("email")
    @Expose
    @Bindable
    private String email;

    @SerializedName("username")
    @Expose
    @Bindable
    private String username;

    @SerializedName("avatar")
    @Expose
    @Bindable
    private String avatar;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 13.04.2017.
 */

public class Answer extends BaseObservable {
    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;

    @SerializedName("owner")
    @Expose
    @Bindable
    private User owner;

    @SerializedName("image")
    @Expose
    @Bindable
    private String image;

    @SerializedName("parent")
    @Expose
    @Bindable
    private String parent;

    @SerializedName("description")
    @Expose
    @Bindable
    private String description;

    @SerializedName("ratio")
    @Expose
    @Bindable
    private Float ratio;

    @SerializedName("relativeDate")
    @Expose
    @Bindable
    private String relativeDate;
    @SerializedName("myVote")
    @Expose
    @Bindable
    private int myVote;

    @SerializedName("vote")
    @Expose
    @Bindable
    private Integer vote=0;



    @Bindable
    private int height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getRelativeDate() {
        return relativeDate;
    }

    public void setRelativeDate(String relativeDate) {
        this.relativeDate = relativeDate;
    }

    public int getMyVote() {
        return myVote;
    }

    public void setMyVote(int myVote) {
        this.myVote = myVote;
        notifyPropertyChanged(BR.myVote);
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
        notifyPropertyChanged(BR.vote);
    }
}

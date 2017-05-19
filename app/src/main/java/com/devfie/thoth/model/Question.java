package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.devfie.thoth.BaseApplication;
import com.devfie.thoth.R;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public class Question extends BaseObservable {


    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;
    @SerializedName("title")
    @Expose
    @Bindable
    private String title;

    @SerializedName("description")
    @Expose
    @Bindable
    private String description;

    @SerializedName("date")
    @Expose
    @Bindable
    private String date;

    @SerializedName("owner")
    @Expose
    @Bindable
    private User owner;

    @SerializedName("isLiked")
    @Expose
    @Bindable
    private Boolean liked = false;

    @SerializedName("likeCount")
    @Expose
    @Bindable
    private Integer likeCount;

    @SerializedName("answerCount")
    @Expose
    @Bindable
    private Integer answerCount;

    @SerializedName("image")
    @Expose
    @Bindable
    private String image;

    @SerializedName("categories")
    @Expose
    @Bindable
    private List<Category> categories;

    @SerializedName("likes")
    @Expose
    @Bindable
    private List<User> likes;

    @SerializedName("answers")
    @Expose
    @Bindable
    private List<Answer> answers;

    @SerializedName("ratio")
    @Expose
    @Bindable
    private Float ratio;

    @SerializedName("relativeDate")
    @Expose
    @Bindable
    private String relativeDate;


    @Bindable
    private int height;

    @Bindable
    public String getAnswerCountText() {
        return String.valueOf(String.valueOf(answerCount) + " " + BaseApplication.getInstance().getString(R.string.answer));
    }

    @Bindable
    public String getBookmarkCountText() {
        return String.valueOf(likeCount + " " + BaseApplication.getInstance().getString(R.string.bookmark));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
        notifyPropertyChanged(BR.liked);
        // notifyPropertyChanged(BR.bookmarkCountText);
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
        notifyPropertyChanged(BR.likeCount);
        notifyPropertyChanged(BR.bookmarkCountText);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
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

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public String getRelativeDate() {
        return relativeDate;
    }

    public void setRelativeDate(String relativeDate) {
        this.relativeDate = relativeDate;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

package com.devfie.thoth.events;

/**
 * Created by mac-onur on 16.04.2017.
 */

public class OnQuestionFollowStateChange {

    private Boolean isFollow;
    private String questionId;
    private int followCount;

    public OnQuestionFollowStateChange(Boolean isFollow, String questionId, int followCount) {
        this.isFollow = isFollow;
        this.questionId = questionId;
        this.followCount = followCount;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }
}

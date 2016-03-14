package com.khangvu.instagramclone;

/**
 * Created by duyvu on 3/14/16.
 */
public class Comment {
    private String mUserName, mComment,mProfileImageUrl, mTimeStamp;

    public Comment() {

    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String userName) {
        this.mUserName = userName;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String comment) {
        this.mComment = comment;
    }

    public String getmProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void setmProfileImageUrl(String url) {
        this.mProfileImageUrl = url;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(String timeStamp) {
        this.mTimeStamp = timeStamp;
    }
}

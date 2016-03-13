package com.khangvu.instagramclone;

/**
 * Created by duyvu on 3/12/16.
 */
public class Photo {
    private String mUserName, mCaption, mImageUrl, mProfileImageUrl, mTimeStamp;
    private int mLikeCount;

    public Photo() {
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String userName) {
        this.mUserName = userName;
    }

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String caption) {
        this.mCaption = caption;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String url) {
        this.mImageUrl = url;
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

    public int getmLikeCount() {
        return mLikeCount;
    }

    public void setmLikeCount(int likeCount) {
        this.mLikeCount = likeCount;
    }

}

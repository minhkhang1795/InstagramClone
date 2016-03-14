package com.khangvu.instagramclone;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by duyvu on 3/12/16.
 */
public class Photo {
    private String mUserName, mCaption, mImageUrl, mProfileImageUrl, mTimeStamp, mMediaId;
    private int mLikeCount, mCommentCount;
    private ArrayList<Comment> mComments;

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

    public String getmMediaId() {
        return mMediaId;
    }

    public void setmMediaId(String mediaId) {
        this.mMediaId = mediaId;
    }

    public int getmLikeCount() {
        return mLikeCount;
    }

    public void setmLikeCount(int likeCount) {
        this.mLikeCount = likeCount;
    }

    public ArrayList<Comment> getmComments() {
        return mComments;
    }

    public void setmComments(JSONArray comments) throws JSONException {
        mComments = new ArrayList<Comment>();
        for (int i = 0; i < comments.length(); i++) {
            Comment comment = new Comment();
            comment.setmTimeStamp(comments.getJSONObject(i).getString("created_time"));
            comment.setmComment(comments.getJSONObject(i).getString("text"));
            comment.setmProfileImageUrl(comments.getJSONObject(i).getJSONObject("from").getString("profile_picture"));
            comment.setmUserName(comments.getJSONObject(i).getJSONObject("from").getString("username"));
            mComments.add(comment);
        }
    }

    public int getmCommentCount() {
        return mCommentCount;
    }
    // Note: comment count is different from comments.length()
    public void setmCommentCount(int commentCount) {
        this.mCommentCount = commentCount;
    }
}

package com.khangvu.instagramclone;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AllCommentsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommentsAdapter mAdapter;
    private ArrayList<Comment> mCommentsList = new ArrayList<>();

    public static final String CLIENT_ID = "2c7a12ed3cf243d69c51d334d4b57dec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments);
        String mediaId = getIntent().getStringExtra("mediaId");
        fetchAllComments(mediaId);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.comment_activity_name));
        mToolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CommentsAdapter(mCommentsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fetchAllComments(String mediaId) {
        String url = "https://api.instagram.com/v1/media/" + mediaId + "/comments?client_id=" + CLIENT_ID;
        // Create a network client
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // On success (work, 200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Inspect a JSONObject
                Log.i("DEBUG", String.valueOf(response));

                // Iterate each of the comment items and decode the item into a java object
                JSONArray commentsJSON = null;
                try {
                    mAdapter.clear();
                    commentsJSON = response.getJSONArray("data"); // array of posts
                    // Iterate array of posts
                    for (int i = 0; i < commentsJSON.length(); i++) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setmUserName(commentJSON.getJSONObject("from").getString("username"));
                        comment.setmComment(commentJSON.getString("text"));
                        comment.setmProfileImageUrl(commentJSON.getJSONObject("from").getString("profile_picture"));
                        comment.setmTimeStamp(commentJSON.getString("created_time"));
                        mCommentsList.add(comment);
                    }
                    mAdapter.addAll(mCommentsList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // On failure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", responseString);
            }
        });
    }
}



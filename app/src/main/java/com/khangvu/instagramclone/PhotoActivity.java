package com.khangvu.instagramclone;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

public class PhotoActivity extends AppCompatActivity {
    private ArrayList<Photo> mPhotosList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PhotosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeContainer;

    public static final String CLIENT_ID = "2c7a12ed3cf243d69c51d334d4b57dec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initToolbar();
        initRecyclerView();
        initSwipeContainer();
        fetchPopularPhotos();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.main_activity_name));
        mToolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PhotosAdapter(mPhotosList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSwipeContainer() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
    }

    public void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        // Create a network client
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // On success (work, 200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Inspect a JSONObject
                Log.i("DEBUG", String.valueOf(response));

                // Iterate each of the photo items and decode the item into a java object
                JSONArray photosJson = null;
                try {
                    mAdapter.clear();
                    photosJson = response.getJSONArray("data"); // array of posts
                    // Iterate array of posts
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.setmUserName(photoJson.getJSONObject("user").getString("username"));
                        photo.setmCaption(photoJson.getJSONObject("caption").getString("text"));
                        photo.setmImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setmProfileImageUrl(photoJson.getJSONObject("user").getString("profile_picture"));
                        photo.setmTimeStamp(photoJson.getJSONObject("caption").getString("created_time"));
                        photo.setmLikeCount(photoJson.getJSONObject("likes").getInt("count"));
                        mPhotosList.add(photo);
                    }
                    mAdapter.addAll(mPhotosList);
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeContainer.setRefreshing(false);
                }
            }

            // On failure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", responseString);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}

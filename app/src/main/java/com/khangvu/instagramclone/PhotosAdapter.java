package com.khangvu.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.khangvu.instagramclone.DeviceDimensionsHelper.getDisplayWidth;

/**
 * Created by duyvu on 3/12/16.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private ArrayList<Photo> mPhotosList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvCaption, tvLikeCount,
                tvTimeStamp, tvViewAllComments, tvCommentOne, tvCommentTwo;
        public ImageView ivPhoto, ivProfileImage;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            tvUserName = ButterKnife.findById(view, R.id.user_name_text_view);
            tvCaption = ButterKnife.findById(view, R.id.caption_text_view);
            tvLikeCount = ButterKnife.findById(view, R.id.like_count_text_view);
            tvTimeStamp = ButterKnife.findById(view, R.id.time_stamp_text_view);
            tvViewAllComments = ButterKnife.findById(view, R.id.view_all_comments_text_view);
            tvCommentOne = ButterKnife.findById(view, R.id.comment_one_text_view);
            tvCommentTwo = ButterKnife.findById(view, R.id.comment_two_text_view);
            ivPhoto = ButterKnife.findById(view, R.id.photo_image_view);
            ivProfileImage = ButterKnife.findById(view, R.id.profile_image_view);
            progressBar = ButterKnife.findById(view, R.id.progress_bar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotosAdapter(ArrayList<Photo> photosList) {
        this.mPhotosList = photosList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_row, parent, false);
        mContext = itemView.getContext();
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Photo photo = mPhotosList.get(position);
        holder.tvUserName.setText(photo.getmUserName());

        // Set Caption: primary main dark color = #125688
        String captionString = "<font color=#125688>" + photo.getmUserName() + " </font> <font color=#000>" + photo.getmCaption() + "</font>";
        holder.tvCaption.setText(Html.fromHtml(captionString));
        final TextView captionView = holder.tvCaption;

        holder.tvCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captionView.getLineCount() < captionView.getMaxLines()) {
                    launchAllCommentsActivity(photo.getmMediaId());
                } else {
                    captionView.setMaxLines(Integer.MAX_VALUE);
                }
            }
        });

        // Set Likes
        String likeCountString = "♥ " + photo.getmLikeCount() + " likes";
        holder.tvLikeCount.setText(Html.fromHtml(likeCountString));

        // Set Time Stamp
        long timeStamp = Long.valueOf(photo.getmTimeStamp()) * 1000;
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(
                timeStamp, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        // Clear out the image view
        holder.ivPhoto.setImageResource(0);
        holder.ivProfileImage.setImageResource(0);
        // Set Image
        float displayWidth = getDisplayWidth(mContext);
        holder.progressBar.setVisibility(View.VISIBLE);
        final ProgressBar progressBar = holder.progressBar;
        Picasso.with(mContext)
                .load(photo.getmImageUrl())
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.default_placeholder))
                .resize((int) displayWidth, 0)
                .into(holder.ivPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        // Set Profile picture
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(mContext)
                .load(photo.getmProfileImageUrl())
                .resize(40, 0)
                .transform(transformation)
                .into(holder.ivProfileImage);

        int numberOfComment = photo.getmComments().size(); // in Media JSON only
        // Set view all comments text view (if all comments > 2)
        if (numberOfComment > 2) {
            String viewAllCommentsText = "View all " + photo.getmCommentCount() + " comments";
            holder.tvViewAllComments.setText(viewAllCommentsText);
            holder.tvViewAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchAllCommentsActivity(photo.getmMediaId());
                }
            });
        } else {
            holder.tvViewAllComments.setVisibility(View.GONE);
        }

        // Set comment 1 text view (if existing)
        if (numberOfComment > 1) {
            String commentOneText = "<font color=#125688>"
                    + photo.getmComments().get(numberOfComment - 1).getmUserName()
                    + " </font> <font color=#000>"
                    + photo.getmComments().get(numberOfComment - 1).getmComment()
                    + "</font>";
            holder.tvCommentTwo.setText(Html.fromHtml(commentOneText));
            holder.tvCommentTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchAllCommentsActivity(photo.getmMediaId());
                }
            });
        } else {
            holder.tvCommentTwo.setVisibility(View.GONE);
        }

        // Set comment 1 text view (if existing)
        if (numberOfComment > 0) {
            String commentTwoText = "<font color=#125688>"
                    + photo.getmComments().get(numberOfComment - 2).getmUserName()
                    + " </font> <font color=#000>"
                    + numberOfComment
                    + photo.getmComments().get(numberOfComment - 2).getmComment()
                    + "</font>";
            holder.tvCommentOne.setText(Html.fromHtml(commentTwoText));
            holder.tvCommentOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchAllCommentsActivity(photo.getmMediaId());
                }
            });
        } else {
            holder.tvCommentOne.setVisibility(View.GONE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPhotosList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPhotosList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Photo> list) {
        mPhotosList.addAll(list);
        notifyDataSetChanged();
    }

    public void launchAllCommentsActivity(String mediaId) {
        Intent i = new Intent(mContext, AllCommentsActivity.class);
        i.putExtra("mediaId", mediaId);
        mContext.startActivity(i);
    }
}

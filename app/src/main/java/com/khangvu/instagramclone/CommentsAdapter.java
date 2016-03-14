package com.khangvu.instagramclone;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by duyvu on 3/14/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {
    private ArrayList<Comment> mCommentsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvComment, tvTimeStamp;
        public ImageView ivProfileImage;

        public MyViewHolder(View view) {
            super(view);
            tvComment = ButterKnife.findById(view, R.id.comment_text_View);
            tvTimeStamp = ButterKnife.findById(view, R.id.time_stamp_text_view);
            ivProfileImage = ButterKnife.findById(view, R.id.profile_image_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentsAdapter(ArrayList<Comment> commentsList) {
        this.mCommentsList = commentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_row, parent, false);
        mContext = itemView.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Comment comment = mCommentsList.get(position);
        // Set Comment
        String commentString = "<font color=#125688>" + comment.getmUserName() + " </font> <font color=#000>" + comment.getmComment() + "</font>";
        holder.tvComment.setText(Html.fromHtml(commentString));

        // Set Time stamp
        long timeStamp = Long.valueOf(comment.getmTimeStamp()) * 1000;
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(
                timeStamp, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        // Set Profile picture
        holder.ivProfileImage.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(mContext)
                .load(comment.getmProfileImageUrl())
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.default_placeholder_circle))
                .resize(40, 0)
                .transform(transformation)
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mCommentsList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Comment> list) {
        mCommentsList.addAll(list);
        notifyDataSetChanged();
    }
}

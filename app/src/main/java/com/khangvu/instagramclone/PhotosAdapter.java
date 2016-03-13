package com.khangvu.instagramclone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import static com.khangvu.instagramclone.DeviceDimensionsHelper.getDisplayWidth;

/**
 * Created by duyvu on 3/12/16.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private ArrayList<Photo> mPhotosList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvCaption, tvLikeCount, tvTimeStamp;
        public ImageView ivPhoto, ivProfileImage;

        public MyViewHolder(View view) {
            super(view);
            tvUserName = (TextView) view.findViewById(R.id.user_name_text_view);
            tvCaption = (TextView) view.findViewById(R.id.caption_text_view);
            tvLikeCount = (TextView) view.findViewById(R.id.like_count_text_view);
            tvTimeStamp = (TextView) view.findViewById(R.id.time_stamp);
            ivPhoto = (ImageView) view.findViewById(R.id.photo_image_view);
            ivProfileImage = (ImageView) view.findViewById(R.id.profile_image_view);
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
        Photo photo = mPhotosList.get(position);
        holder.tvUserName.setText(photo.getmUserName());
        holder.tvCaption.setText(photo.getmCaption());
        holder.tvLikeCount.setText(String.valueOf(photo.getmLikeCount()));
        long timeStamp = Long.valueOf(photo.getmTimeStamp()) * 1000;
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(
                timeStamp, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        // Clear out the image view
        holder.ivPhoto.setImageResource(0);
        holder.ivProfileImage.setImageResource(0);
        // Insert the image using Picasso
        float displayWidth = getDisplayWidth(mContext);
        Picasso.with(mContext).load(photo.getmImageUrl()).resize((int) displayWidth, 0).into(holder.ivPhoto);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(mContext).load(photo.getmProfileImageUrl()).resize(40, 0).transform(transformation).into(holder.ivProfileImage);
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
}

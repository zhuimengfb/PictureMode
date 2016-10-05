package com.fbi.picturemode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbi.picturemode.R;
import com.fbi.picturemode.entity.UnsplashLocation;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.utils.Constants;
import com.fbi.picturemode.utils.GlideUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class NewPictureRecyclerAdapter extends UltimateViewAdapter<NewPictureRecyclerAdapter
    .NewPictureViewHolder> {

  private Context context;
  private List<UnsplashPicture> unsplashPictures;
  private int mode;

  public void setMode(int mode) {
    if (unsplashPictures != null && unsplashPictures.size() > 0) {
      this.mode = mode;
      this.notifyDataSetChanged();
    }
  }

  public NewPictureRecyclerAdapter(Context context, List<UnsplashPicture> unsplashPictures) {
    this.context = context;
    this.unsplashPictures = unsplashPictures;
  }

  @Override
  public NewPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new NewPictureViewHolder(LayoutInflater.from(context).inflate(R.layout.item_picture,
        parent, false));
  }

  @Override
  public NewPictureViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public NewPictureViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public NewPictureViewHolder onCreateViewHolder(ViewGroup parent) {
    return new NewPictureViewHolder(LayoutInflater.from(context).inflate(R.layout.item_picture,
        parent, false));
  }

  @Override
  public void onBindViewHolder(NewPictureViewHolder holder, final int position) {
    UnsplashPicture unsplashPicture = unsplashPictures.get(position);
    GlideUtils.showPicture(holder.picture, unsplashPicture.getUnsplashPictureLinks
        ().getRegular(), unsplashPictures.get(position).getColor());
    GlideUtils.showUserIcon(holder.userIcon, unsplashPicture.getUnsplashUser()
        .getUserProfileImage().getLarge());
    holder.userName.setText(unsplashPicture.getUnsplashUser().getUserName());
    UnsplashLocation unsplashLocation = unsplashPicture.getLocation();
    if (unsplashLocation != null) {
      holder.location.setText(unsplashLocation.getCountry() + " " + unsplashLocation.getCity());
    } else {
      holder.location.setVisibility(View.GONE);
    }
    holder.rootView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (itemClickListener != null && mode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          itemClickListener.onItemClick(view, position);
        } else if (onItemDeleteListener != null && mode == Constants.MANAGE_COLLECT_MODE_DELETE) {
          onItemDeleteListener.onItemDelete(position);
        }
      }
    });
    holder.collect.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (onItemCollectListener != null && mode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          onItemCollectListener.onItemCollect(position);
        }
      }
    });
    if (mode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
      holder.delete.setVisibility(View.GONE);
    } else if (mode == Constants.MANAGE_COLLECT_MODE_DELETE) {
      holder.delete.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getItemCount() {
    return unsplashPictures.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
    return null;
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override
  public int getAdapterItemCount() {
    return unsplashPictures.size();
  }

  @Override
  public long generateHeaderId(int position) {
    return 0;
  }

  class NewPictureViewHolder extends UltimateRecyclerviewViewHolder {
    @BindView(R.id.iv_picture) ImageView picture;
    @BindView(R.id.iv_user_icon) ImageView userIcon;
    @BindView(R.id.tv_user_name) TextView userName;
    @BindView(R.id.tv_location) TextView location;
    @BindView(R.id.iv_collect) ImageView collect;
    @BindView(R.id.iv_delete) ImageView delete;
    View rootView;

    public NewPictureViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      rootView = itemView;
    }
  }

  public interface OnItemDeleteListener {
    void onItemDelete(int position);
  }

  private OnItemDeleteListener onItemDeleteListener;

  public void addOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
    this.onItemDeleteListener = onItemDeleteListener;
  }

  public interface OnItemCollectListener {
    void onItemCollect(int position);
  }

  private OnItemCollectListener onItemCollectListener;

  public void addOnItemCollectListener(OnItemCollectListener onItemCollectListener) {
    this.onItemCollectListener = onItemCollectListener;
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  private OnItemClickListener itemClickListener;

  public void addOnItemClickListener(OnItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
}

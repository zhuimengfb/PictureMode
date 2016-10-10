package com.fbi.picturemode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.entity.MyDownload;
import com.fbi.picturemode.utils.Constants;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class LocalPictureAdapter extends UltimateViewAdapter<LocalPictureAdapter
    .LocalPictureViewHolder> {

  private List<MyDownload> myDownloads;

  public LocalPictureAdapter(List<MyDownload> myDownloads) {
    this.myDownloads = myDownloads;
  }

  @Override
  public LocalPictureViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public LocalPictureViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public LocalPictureViewHolder onCreateViewHolder(ViewGroup parent) {
    return new LocalPictureViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_local_picture, parent, false));
  }

  @Override
  public int getAdapterItemCount() {
    return myDownloads.size();
  }

  @Override
  public long generateHeaderId(int position) {
    return 0;
  }

  @Override
  public void onBindViewHolder(LocalPictureViewHolder holder, final int position) {
    Glide.with(MyApp.getContext()).load(myDownloads.get(position).getLocalAddress()).override
        (400, 200).into(holder.localPicture);
    if (mode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
      holder.delete.setVisibility(View.GONE);
    } else if (mode == Constants.MANAGE_COLLECT_MODE_DELETE) {
      holder.delete.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
    return null;
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  public void setMode(int mode) {
    if (myDownloads.size() > 0) {
      this.mode = mode;
      notifyDataSetChanged();
    }
  }

  private int mode = Constants.MANAGE_COLLECT_MODE_NORMAL;

  class LocalPictureViewHolder extends UltimateRecyclerviewViewHolder {
    @BindView(R.id.iv_local_picture) ImageView localPicture;
    @BindView(R.id.iv_delete) ImageView delete;

    public LocalPictureViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}

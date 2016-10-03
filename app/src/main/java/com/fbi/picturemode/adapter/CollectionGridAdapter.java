package com.fbi.picturemode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.utils.DateUtils;
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

public class CollectionGridAdapter extends UltimateViewAdapter<CollectionGridAdapter
    .CollectionViewHolder> {

  private List<UnsplashCollection> collectionList;

  public CollectionGridAdapter(List<UnsplashCollection> collectionList) {
    this.collectionList = collectionList;
  }

  @Override
  public CollectionViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public CollectionViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public CollectionViewHolder onCreateViewHolder(ViewGroup parent) {
    return new CollectionViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_collection, parent, false));
  }

  @Override
  public int getAdapterItemCount() {
    return collectionList.size();
  }

  @Override
  public long generateHeaderId(int position) {
    return 0;
  }

  @Override
  public void onBindViewHolder(CollectionViewHolder holder, int position) {
    UnsplashCollection collection = collectionList.get(position);
    holder.title.setText(collection.getTitle());
    holder.publishTime.setText(DateUtils.formatDate(collection.getPublishTime()));
    UnsplashPicture cover = collection.getCover();
    if (cover != null) {
      GlideUtils.showPicture(holder.cover, cover.getUnsplashPictureLinks().getSmall(), cover
          .getColor());
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
    return new CollectionViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_collection, parent, false));
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  class CollectionViewHolder extends UltimateRecyclerviewViewHolder {

    @BindView(R.id.iv_cover) ImageView cover;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.tv_publish_time) TextView publishTime;

    public CollectionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

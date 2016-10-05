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
import com.fbi.picturemode.utils.Constants;
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
  private int currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;

  public void setCurrentMode(int mode) {
    this.currentMode = mode;
    if (collectionList != null && collectionList.size() > 0) {
      this.notifyDataSetChanged();
    }
  }

  public CollectionGridAdapter(List<UnsplashCollection> collectionList) {
    this.collectionList = collectionList;
  }

  @Override
  public int getItemCount() {
    return collectionList.size();
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
  public void onBindViewHolder(CollectionViewHolder holder, final int position) {
    UnsplashCollection collection = collectionList.get(position);
    //TODO 暂时不显示标题
//    holder.title.setText(collection.getTitle());
    holder.publishTime.setText(DateUtils.formatDate(collection.getPublishTime()));
    UnsplashPicture cover = collection.getCover();
    if (cover != null) {
      GlideUtils.showPicture(holder.cover, cover.getUnsplashPictureLinks().getRegular(), cover
          .getColor());
    }
    holder.rootView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (itemClickListener != null && currentMode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          itemClickListener.onItemClick(view, position);
        } else if (onItemDeleteListener != null && currentMode == Constants
            .MANAGE_COLLECT_MODE_DELETE) {
          onItemDeleteListener.onItemDelete(position);
        }
      }
    });
    if (currentMode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
      holder.delete.setVisibility(View.GONE);
    } else if (currentMode == Constants.MANAGE_COLLECT_MODE_DELETE) {
      holder.delete.setVisibility(View.VISIBLE);
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

  public interface OnItemDeleteListener {
    void onItemDelete(int position);
  }

  private OnItemDeleteListener onItemDeleteListener;

  public void addOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
    this.onItemDeleteListener = onItemDeleteListener;
  }

  class CollectionViewHolder extends UltimateRecyclerviewViewHolder {

    @BindView(R.id.iv_cover) ImageView cover;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.tv_publish_time) TextView publishTime;
    @BindView(R.id.iv_delete) ImageView delete;
    View rootView;

    public CollectionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      rootView = itemView;
    }
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  private NewPictureRecyclerAdapter.OnItemClickListener itemClickListener;

  public void addOnItemClickListener(NewPictureRecyclerAdapter.OnItemClickListener
                                         itemClickListener) {
    this.itemClickListener = itemClickListener;
  }
}

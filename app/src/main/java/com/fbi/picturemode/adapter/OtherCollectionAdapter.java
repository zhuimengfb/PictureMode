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
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class OtherCollectionAdapter extends RecyclerView.Adapter<OtherCollectionAdapter.CollectionViewHolder> {

  private List<UnsplashCollection> collectionList;

  public OtherCollectionAdapter(List<UnsplashCollection> collectionList) {
    this.collectionList = collectionList;
  }
  @Override
  public OtherCollectionAdapter.CollectionViewHolder onCreateViewHolder(ViewGroup parent, int
      viewType) {
    return new CollectionViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_collection, parent, false));
  }

  @Override
  public void onBindViewHolder(OtherCollectionAdapter.CollectionViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return collectionList.size();
  }

  class CollectionViewHolder extends UltimateRecyclerviewViewHolder {

    @BindView(R.id.iv_cover) ImageView cover;
    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.tv_publish_time) TextView publishTime;
    View rootView;

    public CollectionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      rootView = itemView;
    }
  }
}

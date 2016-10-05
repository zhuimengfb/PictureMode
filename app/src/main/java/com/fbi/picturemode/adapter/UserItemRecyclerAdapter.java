package com.fbi.picturemode.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.entity.UserItem;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UserItemRecyclerAdapter extends UltimateViewAdapter<UserItemRecyclerAdapter
    .UserItemViewHolder> {

  private List<UserItem> userItems;
  private Context context;

  public UserItemRecyclerAdapter(Context context, List<UserItem> userItems) {
    this.context = context;
    this.userItems = userItems;
  }

  @Override
  public int getItemCount() {
    return userItems.size();
  }

  @Override
  public UserItemViewHolder newFooterHolder(View view) {
    return null;
  }

  @Override
  public UserItemViewHolder newHeaderHolder(View view) {
    return null;
  }

  @Override
  public UserItemViewHolder onCreateViewHolder(ViewGroup parent) {
    return new UserItemViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_user_layout, parent, false));
  }

  @Override
  public int getAdapterItemCount() {
    return userItems.size();
  }

  @Override
  public long generateHeaderId(int position) {
    return 0;
  }

  @Override
  public void onBindViewHolder(UserItemViewHolder holder, final int position) {
    holder.userItemIcon.setImageResource(userItems.get(position).getIconRes());
    holder.userItemTitle.setText(userItems.get(position).getTitle());
    if (!TextUtils.isEmpty(userItems.get(position).getSubTitle())) {
      holder.userItemSubtitle.setVisibility(View.VISIBLE);
      holder.userItemSubtitle.setText(userItems.get(position).getSubTitle());
    }
    holder.getView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(context, userItems.get(position).getClazz());
        context.startActivity(intent);
      }
    });
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
    return null;
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  class UserItemViewHolder extends UltimateRecyclerviewViewHolder {
    @BindView(R.id.iv_user_item_icon) ImageView userItemIcon;
    @BindView(R.id.tv_user_item_title) TextView userItemTitle;
    @BindView(R.id.tv_user_item_subtitle) TextView userItemSubtitle;
    View rootView;

    public UserItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      rootView = itemView;
    }
  }
}

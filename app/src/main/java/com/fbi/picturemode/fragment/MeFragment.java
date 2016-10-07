package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.MyCollectionActivity;
import com.fbi.picturemode.activity.MyDownloadActivity;
import com.fbi.picturemode.activity.SettingActivity;
import com.fbi.picturemode.activity.widget.MyLinearLayoutManager;
import com.fbi.picturemode.adapter.UserItemRecyclerAdapter;
import com.fbi.picturemode.entity.UserItem;
import com.fbi.picturemode.utils.GlideUtils;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class MeFragment extends BaseFragment {

  @BindView(R.id.iv_user_icon) ImageView userIcon;
  @BindView(R.id.tv_user_name) TextView userName;
  @BindView(R.id.user_recycler_view) RecyclerView userRecyclerView;
  @BindView(R.id.iv_settings) ImageView settings;
  private List<UserItem> userItems = new ArrayList<>();
  private UserItemRecyclerAdapter adapter;

  public static MeFragment newInstance() {
    MeFragment meFragment = new MeFragment();
    return meFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_me, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initView() {
    MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(getActivity(),
        LinearLayoutManager.VERTICAL, false);
    userRecyclerView.setLayoutManager(layoutManager);
    userRecyclerView.setItemAnimator(new ScaleInAnimator());

  }

  private void initData() {
    String url = UserSharedPreferences.getInstance(getActivity()).getUserIconUrl();
    if (!TextUtils.isEmpty(url)) {
      Glide.with(this).load(url).error(R.drawable.default_avatar_picture).bitmapTransform(new
          CropCircleTransformation(MyApp.getContext())).into(userIcon);
    } else {
      GlideUtils.showUserIcon(userIcon, url, R.drawable.default_avatar_picture);
    }
    userName.setText(getString(R.string.app_name));
    userItems.add(new UserItem(getString(R.string.my_download), "", R.drawable.tag_download,
        MyDownloadActivity.class));
    userItems.add(new UserItem(getString(R.string.my_collections), "", R.drawable.tag_like,
        MyCollectionActivity.class));
    adapter = new UserItemRecyclerAdapter(getActivity(), userItems);
    userRecyclerView.setAdapter(adapter);
  }

  private void initEvent() {
    settings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SettingActivity.toThisActivity(getActivity());
      }
    });
  }

  @Override
  public void initPresenter() {

  }

  @Override
  public void destroyPresenter() {

  }
}

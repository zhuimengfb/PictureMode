package com.fbi.picturemode.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fbi.picturemode.MainActivity;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.LaunchView;
import com.fbi.picturemode.presenter.LaunchPresenter;
import com.fbi.picturemode.utils.GlideUtils;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class LaunchActivity extends BaseActivity implements LaunchView {

  @BindView(R.id.iv_launch_picture) ImageView launchPicture;
  @BindView(R.id.preload) ImageView preload;
  @BindView(R.id.iv_user_icon) ImageView userIcon;
  @BindView(R.id.tv_user_name) TextView userName;
  @BindView(R.id.tv_location) TextView location;
  @BindView(R.id.bt_skip) Button skipButton;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  private LaunchPresenter launchPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    loadingLayout.setVisibility(View.VISIBLE);
    launchPresenter.showLastPicture();
    launchPresenter.getRandomPicture();
    Log.d("path", "onCreate: " + UserSharedPreferences.getInstance(this).getUserPhotoBasePath());
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_launch);
  }

  @OnClick(R.id.bt_skip)
  public void skip() {
    MainActivity.toMainActivityAndKillSelf(LaunchActivity.this);
  }

  @Override
  public void initPresenter() {
    launchPresenter = new LaunchPresenter(this);
  }

  @Override
  public void showPicture(String url, String color) {
    GlideUtils.showPicture(launchPicture, url, color);
  }

  @Override
  public void showUserIcon(String url) {
    GlideUtils.showUserIcon(userIcon, url);
  }

  @Override
  public void showLocation(String location) {
    if (!TextUtils.isEmpty(location)) {
      this.location.setText(location);
    } else {
      this.location.setVisibility(View.GONE);
    }
  }

  public void preload(String url) {
    Glide.with(this).load(Uri.parse(url)).into(preload);
  }

  @Override
  public void showUserName(String userName) {
    this.userName.setText(userName);
  }

  @Override
  public void hideLoading() {
    loadingLayout.setVisibility(View.GONE);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    launchPresenter.onDestroy();
  }
}

package com.fbi.picturemode.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.picturemode.MainActivity;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.LaunchView;
import com.fbi.picturemode.presenter.LaunchPresenter;
import com.fbi.picturemode.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class LaunchActivity extends BaseActivity implements LaunchView {

  @BindView(R.id.iv_launch_picture) ImageView launchPicture;
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
    setContentView(R.layout.activity_launch);
    ButterKnife.bind(this);
    loadingLayout.setVisibility(View.VISIBLE);
    launchPresenter.showLastPicture();
    launchPresenter.getRandomPicture();
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

package com.fbi.picturemode.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fbi.picturemode.MainActivity;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.LaunchView;
import com.fbi.picturemode.presenter.LaunchPresenter;
import com.fbi.picturemode.utils.GlideUtils;
import com.fbi.picturemode.utils.NetworkUtils;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bumptech.glide.Glide.with;

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
  @BindView(R.id.bt_set_wallpaper) Button setWallpaper;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  private LaunchPresenter launchPresenter;
  private File localFile;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    launchPresenter.showLastPicture();
    if (NetworkUtils.isWifiNetwork() || isMobileNetworkAndUserPermitChange()) {
      launchPresenter.getRandomPicture();
    }
  }

  private boolean isMobileNetworkAndUserPermitChange() {
    return NetworkUtils.isMobileNetwork() && UserSharedPreferences
        .getInstance(MyApp.getContext()).getDownloadHighQualityPictureUsingMobileNetwork();
  }
  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_launch);
  }

  @OnClick(R.id.bt_skip)
  public void skip() {
    MainActivity.toMainActivityAndKillSelf(LaunchActivity.this);
  }

  @OnClick(R.id.bt_set_wallpaper)
  public void setWallpaper() {
    launchPresenter.setWallpaper();
  }

  @Override
  public void initPresenter() {
    launchPresenter = new LaunchPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    launchPresenter.onDestroy();
  }

  @Override
  public void showPicture(String url, String color) {
    launchPicture.setBackgroundColor(Color.parseColor(color));
    with(MyApp.getContext()).load(Uri.parse(url)).listener(new RequestListener<Uri,
        GlideDrawable>() {

      @Override
      public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean
          isFirstResource) {
        return false;
      }

      @Override
      public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable>
          target, boolean isFromMemoryCache, boolean isFirstResource) {
        hideLoading();
        return false;
      }
    }).into(launchPicture);
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

  public void showLoading() {
    loadingLayout.setVisibility(View.VISIBLE);
  }


  @Override
  public void showLocalPicture(String lastLocalPath, String lastRandomPictureLink, String
      lastRandomColor) {
    launchPicture.setBackgroundColor(Color.parseColor(lastRandomColor));
    showLoading();
    localFile = new File(lastLocalPath);
    if (localFile.exists()) {
      Log.d("from disk", "true");
      Glide.with(this).load(localFile).listener(new RequestListener<File, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean
            isFirstResource) {
          return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable>
            target, boolean isFromMemoryCache, boolean isFirstResource) {
          hideLoading();
          Log.d("source", "onResourceReady: " + isFromMemoryCache);
          return false;
        }
      }).into(launchPicture);
    } else {
      Log.d("from net", "true");
      showPicture(lastRandomPictureLink, lastRandomColor);
    }
  }

  @Override
  public void showDefaultPicture() {
    Glide.with(this).load(R.drawable.default_picture).into(launchPicture);
  }

  @Override
  public void hideSetWallpaper() {
    setWallpaper.setVisibility(View.GONE);
  }

  @Override
  public void showSetWallpaperSuccess() {
    Snackbar.make(launchPicture, R.string.set_wallpaper_success, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showSetWallpaperFail() {
    Snackbar.make(launchPicture, R.string.set_wallpaper_fail, Snackbar.LENGTH_SHORT).show();
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
    if (localFile != null) {
//      boolean success = localFile.delete();
    }
  }
}

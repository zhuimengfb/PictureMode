package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.FullPictureView;
import com.fbi.picturemode.db.MyDownloadDataHelper;
import com.fbi.picturemode.db.UnsplashPictureDataHelper;
import com.fbi.picturemode.entity.MyDownload;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.presenter.FullPicturePresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class FullPictureActivity extends BaseActivity implements FullPictureView {

  @BindView(R.id.iv_full_picture) ImageView fullImage;
  @BindView(R.id.iv_back) ImageView backImage;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  @BindView(R.id.iv_download) ImageView download;
  @BindView(R.id.iv_share) ImageView share;
  @BindView(R.id.iv_collect) ImageView collect;
  @BindView(R.id.operation_layout) RelativeLayout operationLayout;
  private UnsplashPicture picture;
  private MyDownload myDownload;
  PhotoViewAttacher mAttacher;
  private static final int MODE_NET = 0;
  private static final int MODE_LOCAL = 1;

  private FullPicturePresenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int mode = getIntent().getIntExtra("mode", MODE_NET);
    if (mode == 0) {
      picture = (UnsplashPicture) getIntent().getSerializableExtra("picture");
      initData();
    } else if (mode == 1) {
      myDownload = (MyDownload) getIntent().getSerializableExtra("download");
      if (!new File(myDownload.getLocalAddress()).exists()) {
        MyDownloadDataHelper.getInstance(MyApp.getContext()).deleteMyDownload(myDownload.getPhotoId());
        picture = UnsplashPictureDataHelper.getInstance(MyApp.getContext()).queryPictureById
            (myDownload.getPhotoId());
        initData();
      } else {
        operationLayout.setVisibility(View.GONE);
        initLocalData();
      }
    }
    initEvent();
  }

  private void initLocalData() {
    showLoading();
    mAttacher = new PhotoViewAttacher(fullImage);
    Glide.with(this).load(myDownload.getLocalAddress()).into(fullImage);
    mAttacher.update();
    stopLoading();
  }

  @OnClick(R.id.iv_back)
  public void back() {
    finish();
  }

  @OnClick(R.id.iv_collect)
  public void collectPicture() {
    collect.setClickable(false);
    presenter.collectPicture(picture.getId());
  }

  @OnClick(R.id.iv_download)
  public void downloadPicture() {
    download.setClickable(false);
    presenter.downloadPicture(picture);
  }

  private void initEvent() {

  }

  private void initData() {
    showLoading();
    mAttacher = new PhotoViewAttacher(fullImage);
    Glide.with(this).load(Uri.parse(picture.getUnsplashPictureLinks().getRegular())).listener(new RequestListener<Uri, GlideDrawable>() {

      @Override
      public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean
          isFirstResource) {
        return false;
      }

      @Override
      public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable>
          target, boolean isFromMemoryCache, boolean isFirstResource) {
        stopLoading();
        mAttacher.update();
        return false;
      }
    }).into(fullImage);
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_full_picture);
  }

  @Override
  public void initPresenter() {
    presenter = new FullPicturePresenter(this);
  }

  public static void toThisActivityFromNet(Context context, UnsplashPicture picture) {
    Intent intent = new Intent();
    intent.setClass(context, FullPictureActivity.class);
    intent.putExtra("picture", picture);
    intent.putExtra("mode", MODE_NET);
    context.startActivity(intent);
  }

  public static void toThisActivityFromLocal(Context context, MyDownload download) {
    Intent intent = new Intent();
    intent.setClass(context, FullPictureActivity.class);
    intent.putExtra("download", download);
    intent.putExtra("mode", MODE_LOCAL);
    context.startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void showCollectAlready() {
    Snackbar.make(fullImage, R.string.collect_already, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showCollectSuccess() {
    Snackbar.make(fullImage, R.string.collect_success, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showDownloadAlready() {
    Snackbar.make(fullImage, R.string.download_already, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showDownloadSuccess() {
    Snackbar.make(fullImage, R.string.download_success, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showDownloadFail() {
    download.setClickable(true);
    Snackbar.make(fullImage, R.string.download_fail, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showLoading() {
    loadingLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void stopLoading() {
    loadingLayout.setVisibility(View.GONE);
  }

}

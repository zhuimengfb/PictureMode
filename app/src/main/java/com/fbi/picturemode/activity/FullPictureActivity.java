package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.fbi.picturemode.utils.SetWallpaperUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class FullPictureActivity extends BaseActivity implements FullPictureView {

  @BindView(R.id.iv_full_picture) ImageViewTouch fullImage;
  @BindView(R.id.iv_back) ImageView backImage;
  @BindView(R.id.back_layout) RelativeLayout backLayout;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  @BindView(R.id.iv_download) ImageView download;
  @BindView(R.id.iv_share) ImageView share;
  @BindView(R.id.iv_collect) ImageView collect;
  @BindView(R.id.iv_set_wallpaper) ImageView setWallpaper;

  @BindView(R.id.operation_layout) LinearLayout operationLayout;
  @BindView(R.id.layout_download) LinearLayout downloadLayout;
  @BindView(R.id.layout_collect) LinearLayout collectLayout;
  @BindView(R.id.layout_share) LinearLayout shareLayout;
  @BindView(R.id.layout_set_wallpaper) LinearLayout setWallpaperLayout;

  private UnsplashPicture picture;
  private MyDownload myDownload;
  private static final int MODE_NET = 0;
  private static final int MODE_LOCAL = 1;

  private FullPicturePresenter presenter;
  private static float highResolutionThreshold = 5760.0f;
  private float currentWith = 0;
  private long compressThreshold = 3 * 1024 * 1024;
  private boolean loadComplete = false;
  private long waitingPeriod = 4000;
  private int currentMode = MODE_NET;
  private float currentWidthPixels = 0;
  private float currentHeightPixels = 0;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindowWidth();
    currentMode = getIntent().getIntExtra("mode", MODE_NET);
    if (currentMode == MODE_NET) {
      picture = (UnsplashPicture) getIntent().getSerializableExtra("picture");
      myDownload = MyDownloadDataHelper.getInstance(MyApp.getContext())
          .queryMyDownloadByPhotoId(picture.getId());
      //若壁纸已经下载,进入本地模式
      if (myDownload != null && new File(myDownload.getLocalAddress()).exists()) {
        currentMode = MODE_LOCAL;
        initLocalData();
      } else {
        initData();
      }
    } else if (currentMode == MODE_LOCAL) {
      myDownload = (MyDownload) getIntent().getSerializableExtra("download");
      //如果本地壁纸被删除了,进入网络模式
      if (!new File(myDownload.getLocalAddress()).exists()) {
        MyDownloadDataHelper.getInstance(MyApp.getContext()).deleteMyDownload(myDownload
            .getPhotoId());
        picture = UnsplashPictureDataHelper.getInstance(MyApp.getContext()).queryPictureById
            (myDownload.getPhotoId());
        currentMode = MODE_NET;
        initData();
      } else {
        initLocalData();
      }
    }
    initOperationBar();
    initEvent();
  }

  private void initOperationBar() {
    if (currentMode == MODE_NET) {

    } else if (currentMode == MODE_LOCAL) {
      downloadLayout.setVisibility(View.GONE);
    }
  }

  private void getWindowWidth() {
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    float width = dm.widthPixels * dm.density;
    float height = dm.heightPixels * dm.density;
    currentWith = width;
    currentWidthPixels = dm.widthPixels;
    currentHeightPixels = dm.heightPixels;
  }

  private void initLocalData() {
    showLoading();
    File file = new File(myDownload.getLocalAddress());
    Glide.with(MyApp.getContext()).load(file).override((int)currentWidthPixels, (int)currentHeightPixels)
        .into(fullImage);
    stopLoading();
    /*if (file.length() > compressThreshold) {
      Luban.get(this).load(file).putGear(Luban.THIRD_GEAR).setCompressListener(new OnCompressListener() {

        @Override
        public void onStart() {
          showLoading();
        }

        @Override
        public void onSuccess(File file) {
          Glide.with(MyApp.getContext()).load(file).into(fullImage);
          stopLoading();
        }

        @Override
        public void onError(Throwable e) {

        }
      }).launch();
    } else {
      Glide.with(MyApp.getContext()).load(file).into(fullImage);
      stopLoading();
    }*/
  }

  @OnClick(R.id.back_layout)
  public void back() {
    finish();
  }

  @OnClick(R.id.layout_collect)
  public void collectPicture() {
    collectLayout.setClickable(false);
    presenter.collectPicture(picture.getId());
  }

  @OnClick(R.id.layout_download)
  public void downloadPicture() {
    downloadLayout.setClickable(false);
    presenter.downloadPicture(picture);
  }

  private void setWallpaperFromFile(File fromFile) {
    try {
      SetWallpaperUtils.setWallpaperFromFile(fromFile);
      showSetWallpaperSuccess();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @OnClick(R.id.layout_set_wallpaper)
  public void setWallpaper() {
    setWallpaperLayout.setClickable(false);
    if (currentMode == MODE_LOCAL) {
      File file = new File(myDownload.getLocalAddress());
      setWallpaperFromFile(file);
      showSetWallpaperSuccess();
    } else {
      showLoading();
      presenter.setWallpaperFromNet(picture);
    }
  }

  @Override
  public void showSetWallpaperSuccess() {
    Snackbar.make(operationLayout, R.string.set_wallpaper_success, Snackbar.LENGTH_SHORT).show();
    setWallpaperLayout.setClickable(true);
  }

  @Override
  public void showSetWallpaperFail() {
    Snackbar.make(operationLayout, R.string.set_wallpaper_fail, Snackbar.LENGTH_SHORT).show();
    setWallpaperLayout.setClickable(true);
  }

  @Override
  public void showAlreadySetWallpaper() {
    Snackbar.make(operationLayout, R.string.set_wallpaper_already, Snackbar.LENGTH_SHORT).show();
    setWallpaperLayout.setClickable(true);
  }

  private void initEvent() {

  }

  private void initData() {
    showLoading();
    loadingRegularPicture();
  }


  private void loadingRegularPicture() {
    showLoading();
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
        Log.d("width", "" + resource.getCurrent().getIntrinsicWidth());
        int picWidth = resource.getCurrent().getIntrinsicWidth();
        if (currentWidthPixels != 0) {
          float scale = currentWidthPixels / picWidth;
          Log.d("scale", scale + "");
          fullImage.setScaleX(scale);
          fullImage.setScaleY(scale);
        }
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

  @Override
  public void destroyPresenter() {
    presenter.onDestroy();
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
    currentMode = MODE_LOCAL;
    myDownload = MyDownloadDataHelper.getInstance(MyApp.getContext()).queryMyDownloadByPhotoId
        (picture.getId());
    initOperationBar();
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

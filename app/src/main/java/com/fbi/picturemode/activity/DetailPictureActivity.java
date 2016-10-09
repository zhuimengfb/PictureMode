package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.DetailPictureView;
import com.fbi.picturemode.activity.widget.MyLinearLayoutManager;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashExif;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.PictureDetailPresenter;
import com.fbi.picturemode.utils.GlideUtils;
import com.fbi.picturemode.utils.NetworkUtils;
import com.fbi.picturemode.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class DetailPictureActivity extends BaseActivity implements DetailPictureView,
    MyCollectView {

  private UnsplashPicture picture;
  @BindView(R.id.iv_toolbar) ImageView cover;
  @BindView(R.id.iv_user_icon) ImageView userIcon;
  @BindView(R.id.tv_user_name) TextView userName;
  @BindView(R.id.tv_user_bio) TextView userBio;
  @BindView(R.id.related_picture_layout) RelativeLayout otherPicturesLayout;
  @BindView(R.id.loading_more) RelativeLayout loadingMore;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.tv_camera) TextView camera;
  @BindView(R.id.tv_location) TextView location;
  @BindView(R.id.tag_location) ImageView locationImage;
  @BindView(R.id.tv_personal_web) TextView personalWeb;
  @BindView(R.id.tag_web) ImageView personalWebImage;

  private MyCollectPresenter collectPresenter;
  private PictureDetailPresenter presenter;
  private NewPictureRecyclerAdapter adapter;
  private List<UnsplashPicture> pictures = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StatusBarUtils.setTranslucentStatus(this, true);
    picture = (UnsplashPicture) getIntent().getSerializableExtra("picture");
    setTitle(picture.getUnsplashUser().getUserName());
    enableDisplayHomeAsUp();
    showData(picture);
    presenter.getDetailPicture(picture.getId());
    initEvent();
  }

  private void initEvent() {
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        picture = pictures.get(position);
        presenter.getDetailPicture(picture.getId());
        updateCover(picture);
        getSupportActionBar().setTitle(picture.getUnsplashUser().getUserName());
        FullPictureActivity.toThisActivityFromNet(DetailPictureActivity.this, picture);
      }
    });
    adapter.addOnItemCollectListener(new NewPictureRecyclerAdapter.OnItemCollectListener() {
      @Override
      public void onItemCollect(int position) {
        collectPresenter.collectPicture(pictures.get(position).getId());
      }
    });
  }

  @OnClick(R.id.iv_toolbar)
  public void clickPicture() {
    FullPictureActivity.toThisActivityFromNet(this, picture);
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_picture_detail);
  }

  private void updateCover(UnsplashPicture picture) {
    GlideUtils.showPicture(cover, picture.getUnsplashPictureLinks().getRegular(), picture
        .getColor());
  }

  private void showData(UnsplashPicture picture) {
    updateCover(picture);
    GlideUtils.showUserIcon(userIcon, picture.getUnsplashUser().getUserProfileImage().getLarge());
    userName.setText(picture.getUnsplashUser().getName());
    if (TextUtils.isEmpty(picture.getUnsplashUser().getBio())) {
      userBio.setVisibility(View.GONE);
    } else {
      userBio.setText(picture.getUnsplashUser().getBio());
    }

    if (TextUtils.isEmpty(picture.getUnsplashUser().getPortfolioUrl())) {
      hidePortfolio();
    } else {
      showPortfolio(picture.getUnsplashUser().getPortfolioUrl());
    }
    if (TextUtils.isEmpty(picture.getUnsplashUser().getLocation())) {
      hideLocation();
    } else {
      showUserLocation(picture.getUnsplashUser().getLocation());
    }
    if (picture.getUnsplashUser().getTotalPhotos() > 0) {
      otherPicturesLayout.setVisibility(View.VISIBLE);
      MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this,
          LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(linearLayoutManager);
      recyclerView.setItemAnimator(new ScaleInAnimator());
      recyclerView.setNestedScrollingEnabled(false);
      adapter = new NewPictureRecyclerAdapter(this, pictures);
      recyclerView.setAdapter(adapter);
//      recyclerView.reenableLoadmore();
      presenter.getFirstPagePictures(picture.getUnsplashUser().getUserName());
      final String userName = picture.getUnsplashUser().getUserName();
      /*recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
        @Override
        public void loadMore(int itemsCount, int maxLastVisiblePosition) {
          presenter.getNextPagePictures(userName);
        }
      });*/
    } else {
      loadingMore.setVisibility(View.GONE);
      otherPicturesLayout.setVisibility(View.GONE);
    }
  }

  private void hidePortfolio() {
    personalWebImage.setVisibility(View.GONE);
    personalWeb.setVisibility(View.GONE);
  }

  private void showPortfolio(String url) {
    //TODO 暂时不显示
    hidePortfolio();

    /*personalWebImage.setVisibility(View.VISIBLE);
    personalWeb.setVisibility(View.VISIBLE);
    personalWeb.setText(url);*/
  }

  private void hideLocation() {
    locationImage.setVisibility(View.GONE);
    location.setVisibility(View.GONE);
  }

  private void showUserLocation(String location) {
    locationImage.setVisibility(View.VISIBLE);
    this.location.setVisibility(View.VISIBLE);
    this.location.setText(location);
  }

  @Override
  public void initPresenter() {
    presenter = new PictureDetailPresenter(this);
    collectPresenter = new MyCollectPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    presenter.onDestroy();
    collectPresenter.onDestroy();
  }


  public static void toThisActivity(Context context, UnsplashPicture picture) {
    Intent intent = new Intent();
    intent.setClass(context, DetailPictureActivity.class);
    intent.putExtra("picture", picture);
    context.startActivity(intent);
  }

  @Override
  public void updatePictureInfo(UnsplashPicture unsplashPicture) {
    picture = unsplashPicture;
  }

  @Override
  public void updateCameraInfo(UnsplashExif exifInfo) {
    if (exifInfo != null) {
      camera.setText(exifInfo.getModel());
    }
  }

  @Override
  public void updatePictureList(List<UnsplashPicture> unsplashPictures) {
    this.pictures.addAll(unsplashPictures);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateFirstPageList(List<UnsplashPicture> unsplashPictures) {
    this.pictures.clear();
    this.pictures.addAll(unsplashPictures);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void startLoadingMore() {
    loadingMore.setVisibility(View.VISIBLE);
  }

  @Override
  public void loadingComplete() {
    loadingMore.setVisibility(View.GONE);
  }

  @Override
  public void showCollectAlready() {
    Snackbar.make(recyclerView, getString(R.string.collect_already), Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showCollectSuccess() {
    Snackbar.make(recyclerView, getString(R.string.collect_success), Snackbar.LENGTH_SHORT).show();
  }

  @Override
  public void showDeleteSuccess(int position) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
    collectPresenter.onDestroy();
  }

}

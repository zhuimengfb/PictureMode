package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.DetailPictureView;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.entity.UnsplashExif;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.presenter.PictureDetailPresenter;
import com.fbi.picturemode.utils.GlideUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class PictureDetailActivity extends BaseActivity implements DetailPictureView {

  private UnsplashPicture picture;
  @BindView(R.id.iv_toolbar) ImageView cover;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.iv_user_icon) ImageView userIcon;
  @BindView(R.id.tv_user_name) TextView userName;
  @BindView(R.id.tv_user_bio) TextView userBio;
  @BindView(R.id.layout_camera) RelativeLayout cameraLayout;
  @BindView(R.id.tv_camera_info) TextView cameraInfo;
  @BindView(R.id.related_picture_layout) RelativeLayout otherPicturesLayout;
  @BindView(R.id.loading_more) RelativeLayout loadingMore;
  @BindView(R.id.recycler_view) UltimateRecyclerView recyclerView;
  @BindView(R.id.tv_camera) TextView camera;
  private PictureDetailPresenter presenter;
  private NewPictureRecyclerAdapter adapter;
  private List<UnsplashPicture> pictures = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_picture_detail);
    ButterKnife.bind(this);
    picture = (UnsplashPicture) getIntent().getSerializableExtra("picture");
    initToolbar();
    showData(picture);
    presenter.getDetailPicture(picture.getId());
    initEvent();
  }

  private void initEvent() {
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        picture = pictures.get(position);
        presenter.getDetailPicture(picture.getId());
        updateCover(picture);
        getSupportActionBar().setTitle(picture.getUnsplashUser().getUserName());
      }
    });
  }

  private void initToolbar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(picture.getUnsplashUser().getUserName());
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
  }

  private void updateCover(UnsplashPicture picture) {
    GlideUtils.showPicture(cover, picture.getUnsplashPictureLinks().getRegular(), picture
        .getColor());
  }

  private void showData(UnsplashPicture picture) {
    updateCover(picture);
    GlideUtils.showUserIcon(userIcon, picture.getUnsplashUser().getUserProfileImage().getMedium());
    userName.setText(picture.getUnsplashUser().getName());
    if (TextUtils.isEmpty(picture.getUnsplashUser().getBio())) {
      userBio.setVisibility(View.GONE);
    } else {
      userBio.setText(picture.getUnsplashUser().getBio());
    }
    if (picture.getUnsplashUser().getTotalPhotos() > 0) {
      otherPicturesLayout.setVisibility(View.VISIBLE);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(linearLayoutManager);
      recyclerView.setItemAnimator(new ScaleInAnimator());
      adapter = new NewPictureRecyclerAdapter(this, pictures);
      recyclerView.setAdapter(adapter);
      recyclerView.reenableLoadmore();
      presenter.getFirstPagePictures(picture.getUnsplashUser().getUserName());
      final String userName= picture.getUnsplashUser().getUserName();
      recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
        @Override
        public void loadMore(int itemsCount, int maxLastVisiblePosition) {
          presenter.getNextPagePictures(userName);
        }
      });
    } else {
      loadingMore.setVisibility(View.GONE);
      otherPicturesLayout.setVisibility(View.GONE);
    }
  }

  @Override
  public void initPresenter() {
    presenter = new PictureDetailPresenter(this);
  }


  public static void toThisActivity(Context context, UnsplashPicture picture) {
    Intent intent = new Intent();
    intent.setClass(context, PictureDetailActivity.class);
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
      cameraInfo.setText(getString(R.string.camera_info, exifInfo.getModel(), exifInfo
          .getExposureTime(), exifInfo.getAperture(), exifInfo.getFocalLength(), "" + exifInfo
          .getIso()));
      camera.setText(exifInfo.getModel());
      cameraLayout.setVisibility(View.GONE);
    } else {
      cameraLayout.setVisibility(View.GONE);
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
}

package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.DetailCollectionView;
import com.fbi.picturemode.activity.widget.MyLinearLayoutManager;
import com.fbi.picturemode.adapter.CollectionGridAdapter;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.presenter.DetailCollectionPresenter;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.utils.Constants;
import com.fbi.picturemode.utils.GlideUtils;
import com.fbi.picturemode.utils.NetworkUtils;
import com.fbi.picturemode.utils.ShareUtil;
import com.fbi.picturemode.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class DetailCollectionActivity extends BaseActivity implements DetailCollectionView,
    MyCollectView {

  private DetailCollectionPresenter presenter;
  private List<UnsplashPicture> unsplashPictures = new ArrayList<>();
  private UnsplashCollection unsplashCollection;
  private NewPictureRecyclerAdapter adapter;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.iv_toolbar) ImageView cover;
  @BindView(R.id.loading_more) RelativeLayout loadingMore;
  @BindView(R.id.collection_recycler_view) RecyclerView otherCollectionRecyclerView;
  @BindView(R.id.other_collections) RelativeLayout otherCollectionLayout;
  @BindView(R.id.iv_user_icon) ImageView userIcon;
  @BindView(R.id.tv_user_name) TextView userName;
  @BindView(R.id.tv_user_bio) TextView userBio;
  @BindView(R.id.tv_location) TextView location;
  @BindView(R.id.tag_location) ImageView locationImage;
  @BindView(R.id.tv_personal_web) TextView personalWeb;
  @BindView(R.id.tag_web) ImageView personalWebImage;
  private List<UnsplashCollection> otherUnsplashCollections = new ArrayList<>();
  private CollectionGridAdapter collectionGridAdapter;
  private MyCollectPresenter collectPresenter;

  @Override
  public void initPresenter() {
    presenter = new DetailCollectionPresenter(this);
    collectPresenter = new MyCollectPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    presenter.onDestroy();
    collectPresenter.onDestroy();
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    unsplashCollection = (UnsplashCollection) getIntent().getSerializableExtra("collection");
    StatusBarUtils.setTranslucentStatus(this, true);
    initView();
    initData();
    initEvent();
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_collection_detail);
  }

  @OnClick(R.id.iv_toolbar)
  public void showDetailPicture() {
    FullPictureActivity.toThisActivityFromNet(DetailCollectionActivity.this, unsplashCollection
        .getCover());
  }

  private void initEvent() {
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        DetailPictureActivity.toThisActivity(DetailCollectionActivity.this, unsplashPictures.get
            (position));
      }
    });
    adapter.addOnItemCollectListener(new NewPictureRecyclerAdapter.OnItemCollectListener() {
      @Override
      public void onItemCollect(int position) {
        collectPresenter.collectPicture(unsplashPictures.get(position).getId());
      }
    });
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      int lastVisibleItem = 0;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("last item", "" + lastVisibleItem);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter
            .getItemCount()) {
          presenter.getNextPagePictures(unsplashCollection.getId());
        }
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.d("last item", "" + lastVisibleItem);
        lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
            .findLastVisibleItemPosition();
      }
    });

    collectionGridAdapter.addOnItemClickListener(new NewPictureRecyclerAdapter
        .OnItemClickListener() {


      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        unsplashCollection = otherUnsplashCollections.get(position);
        updateData();
      }
    });
  }

  private void initData() {
    adapter = new NewPictureRecyclerAdapter(this, unsplashPictures);
    recyclerView.setAdapter(adapter);
//    recyclerView.reenableLoadmore();

    final int id = unsplashCollection.getId();
//    recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
//      @Override
//      public void loadMore(int itemsCount, int maxLastVisiblePosition) {
//        presenter.getNextPagePictures(id);
//      }
//    });
    collectionGridAdapter = new CollectionGridAdapter(otherUnsplashCollections);
    otherCollectionRecyclerView.setAdapter(collectionGridAdapter);
    updateData();
  }

  private void updateData() {
    presenter.updateCollectionInfo(unsplashCollection.getId());
    presenter.getFirstPagePictures(unsplashCollection.getId());
    presenter.getRelatedCollections(unsplashCollection.getId());
    showUserInfo();
    updateToolbar();
  }

  private void showUserInfo() {
    GlideUtils.showUserIcon(userIcon, unsplashCollection.getUser().getUserProfileImage().getLarge
        ());
    userName.setText(unsplashCollection.getUser().getName());
    if (TextUtils.isEmpty(unsplashCollection.getUser().getBio())) {
      userBio.setVisibility(View.GONE);
    } else {
      userBio.setText(unsplashCollection.getUser().getBio());
    }

    if (TextUtils.isEmpty(unsplashCollection.getUser().getPortfolioUrl())) {
      hidePortfolio();
    } else {
      showPortfolio(unsplashCollection.getUser().getPortfolioUrl());
    }
    if (TextUtils.isEmpty(unsplashCollection.getUser().getLocation())) {
      hideLocation();
    } else {
      showUserLocation(unsplashCollection.getUser().getLocation());
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

  private void updateToolbar() {
    setTitle(unsplashCollection.getTitle());
    GlideUtils.showPicture(cover, unsplashCollection.getCover().getUnsplashPictureLinks()
        .getRegular(), unsplashCollection.getCover().getColor());
  }

  private void initView() {
    enableDisplayHomeAsUp();
    MyLinearLayoutManager picLayoutManager = new MyLinearLayoutManager(this, LinearLayoutManager
        .VERTICAL, false);
    recyclerView.setLayoutManager(picLayoutManager);
    recyclerView.setItemAnimator(new ScaleInAnimator());
    recyclerView.setNestedScrollingEnabled(false);
    showOtherCollectionLayout();
    MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(this, LinearLayoutManager
        .HORIZONTAL, false);
    otherCollectionRecyclerView.setLayoutManager(layoutManager);
    otherCollectionRecyclerView.setItemAnimator(new ScaleInAnimator());
    otherCollectionRecyclerView.setNestedScrollingEnabled(false);
  }

  private void showOtherCollectionLayout() {
    otherCollectionLayout.setVisibility(View.VISIBLE);
  }

  private void hideOtherCollectionLayout() {
    otherCollectionLayout.setVisibility(View.GONE);
  }

  @Override
  public void updatePictureList(List<UnsplashPicture> unsplashPictures) {
    this.unsplashPictures.addAll(unsplashPictures);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateFirstPageList(List<UnsplashPicture> unsplashPictures) {
    this.unsplashPictures.clear();
    this.unsplashPictures.addAll(unsplashPictures);
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
  public void updateCollectionInfo(UnsplashCollection collection) {
    this.unsplashCollection = collection;
    showUserInfo();
  }

  @Override
  public void updateRelatedCollections(List<UnsplashCollection> collections) {
    showOtherCollectionLayout();
    this.otherUnsplashCollections.clear();
    this.otherUnsplashCollections.addAll(collections);
    collectionGridAdapter.notifyDataSetChanged();
  }

  @Override
  public void showNoRelatedCollections() {
    hideOtherCollectionLayout();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.collect:
        collectPresenter.collectCollections(unsplashCollection.getId());
        break;
      case R.id.share:
        ShareUtil.wxShare(getString(R.string.app_name) + "-" + getString(R.string
            .share_collection_title, unsplashCollection.getUser().getName()), unsplashCollection
            .getCover().getUnsplashPictureLinks().getRegular(), unsplashCollection.getUser().getBio
            (), Constants.UNSPLASH_SHARE_COLLECTIONS_URL + unsplashCollection.getId()).share
            (MyApp.getContext());
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.collection_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  public static void toThisActivity(Context context, UnsplashCollection collection) {
    Intent intent = new Intent();
    intent.setClass(context, DetailCollectionActivity.class);
    intent.putExtra("collection", collection);
    context.startActivity(intent);
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

}

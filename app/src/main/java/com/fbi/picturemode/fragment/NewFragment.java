package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.PictureDetailActivity;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.NewListView;
import com.fbi.picturemode.presenter.NewListPresenter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class NewFragment extends BaseFragment implements NewListView {

  @BindView(R.id.recycler_view_new) UltimateRecyclerView recyclerView;
  @BindView(R.id.loading_layout) RelativeLayout loadingMore;
  private NewPictureRecyclerAdapter adapter;
  private NewListPresenter presenter;
  private List<UnsplashPicture> unsplashPictures = new ArrayList<>();

  public static NewFragment newInstance() {
    NewFragment newFragment = new NewFragment();
    return newFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_new, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setItemAnimator(new LandingAnimator());
  }

  private void initData() {
    adapter = new NewPictureRecyclerAdapter(getActivity(), unsplashPictures);
    recyclerView.setAdapter(adapter);
    presenter.getFirstPagePictures();
  }

  private void initEvent() {
    recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        presenter.getFirstPagePictures();
      }
    });
    adapter.setCustomLoadMoreView(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .layout_loading, null));
    recyclerView.reenableLoadmore();
    recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
      @Override
      public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        presenter.getNextPagePictures();
      }
    });
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        PictureDetailActivity.toThisActivity(getActivity(), unsplashPictures.get(position));
      }
    });
  }

  @Override
  public void initPresenter() {
    presenter = new NewListPresenter(this);
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
  public void showRefreshing() {
    recyclerView.setRefreshing(true);
  }

  @Override
  public void stopRefreshing() {
    recyclerView.setRefreshing(false);
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

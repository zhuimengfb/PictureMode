package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailPictureActivity;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.NewListView;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.NewListPresenter;
import com.fbi.picturemode.utils.NetworkUtils;
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

public class NewFragment extends BaseFragment implements NewListView, MyCollectView {

  @BindView(R.id.recycler_view_new) UltimateRecyclerView recyclerView;
  @BindView(R.id.loading_layout) RelativeLayout loadingMore;
  private NewPictureRecyclerAdapter adapter;
  private NewListPresenter presenter;
  private MyCollectPresenter collectPresenter;
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
        new NetworkUtils().isNetworkAvailable(view);
        DetailPictureActivity.toThisActivity(getActivity(), unsplashPictures.get(position));
      }
    });
    adapter.addOnItemCollectListener(new NewPictureRecyclerAdapter.OnItemCollectListener() {
      @Override
      public void onItemCollect(int position) {
        collectPresenter.collectPicture(unsplashPictures.get(position).getId());
      }
    });
  }

  @Override
  public void initPresenter() {
    presenter = new NewListPresenter(this);
    collectPresenter = new MyCollectPresenter(this);
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

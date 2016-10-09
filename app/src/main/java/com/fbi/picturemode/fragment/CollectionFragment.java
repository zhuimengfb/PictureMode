package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailCollectionActivity;
import com.fbi.picturemode.adapter.CollectionGridAdapter;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.fragment.views.CollectionsView;
import com.fbi.picturemode.presenter.CollectionPresenter;
import com.fbi.picturemode.utils.NetworkUtils;
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

public class CollectionFragment extends BaseFragment implements CollectionsView {

  @BindView(R.id.loading_layout) RelativeLayout loadingMore;
  @BindView(R.id.recycler_view) UltimateRecyclerView recyclerView;
  private CollectionGridAdapter adapter;
  private List<UnsplashCollection> collections = new ArrayList<>();
  private CollectionPresenter collectionPresenter;

  public static CollectionFragment newInstance() {
    CollectionFragment collectionFragment = new CollectionFragment();
    return collectionFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_collections, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initView() {
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new ScaleInAnimator());
  }

  private void initData() {
    adapter = new CollectionGridAdapter(collections);
    recyclerView.setAdapter(adapter);
    collectionPresenter.getFirstPageCollections();
  }

  private void initEvent() {
    recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        collectionPresenter.getFirstPageCollections();
      }
    });
    recyclerView.reenableLoadmore();
    recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
      @Override
      public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        collectionPresenter.getNextPageCollections();
      }
    });
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        DetailCollectionActivity.toThisActivity(getActivity(), collections.get(position));
      }
    });
  }

  @Override
  public void initPresenter() {
    collectionPresenter = new CollectionPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    collectionPresenter.onDestroy();
  }

  @Override
  public void updateFirstPageCollections(List<UnsplashCollection> collections) {
    this.collections.clear();
    this.collections.addAll(collections);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void updateNextPageCollections(List<UnsplashCollection> collections) {
    this.collections.addAll(collections);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showRefreshing() {
    recyclerView.setRefreshing(true);
  }

  @Override
  public void hideRefreshing() {
    recyclerView.setRefreshing(false);
  }

  @Override
  public void showLoadMore() {
    loadingMore.setVisibility(View.VISIBLE);
  }

  @Override
  public void loadComplete() {
    loadingMore.setVisibility(View.GONE);
  }

}

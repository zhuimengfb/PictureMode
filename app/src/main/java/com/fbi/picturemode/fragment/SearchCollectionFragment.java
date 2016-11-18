package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fbi.picturemode.fragment.views.SearchCollectionView;
import com.fbi.picturemode.presenter.SearchCollectionPresenter;
import com.fbi.picturemode.utils.NetworkUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 18/10/2016
 */

public class SearchCollectionFragment extends BaseFragment implements SearchCollectionView {

  @BindView(R.id.ultimate_recycler_view) UltimateRecyclerView recyclerView;
  @BindView(R.id.empty_layout) RelativeLayout empty;
  @BindView(R.id.loading_layout) RelativeLayout loadingMore;
  @BindView(R.id.loading) AVLoadingIndicatorView loading;

  private SearchCollectionPresenter collectionPresenter;
  private List<UnsplashCollection> collections = new ArrayList<>();
  private CollectionGridAdapter collectionGridAdapter;
  private String keyword;

  public static SearchCollectionFragment newInstance() {
    Bundle args = new Bundle();
    SearchCollectionFragment fragment = new SearchCollectionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search_collection, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initView() {
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new FadeInAnimator());
  }

  private void initData() {
    collectionGridAdapter = new CollectionGridAdapter(collections);
    recyclerView.setAdapter(collectionGridAdapter);
  }

  private void initEvent() {
    recyclerView.reenableLoadmore();
    recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
      @Override
      public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        showLoadingMore();
        collectionPresenter.getNextPage(keyword);
      }
    });
    collectionGridAdapter.addOnItemClickListener(new NewPictureRecyclerAdapter
        .OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        DetailCollectionActivity.toThisActivity(getActivity(), collections.get(position));
      }
    });
  }

  @Override
  public void initPresenter() {
    collectionPresenter = new SearchCollectionPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    collectionPresenter.onDestroy();
  }

  public void query(String query) {
    keyword = query;
    if (this.collections.size() > 0) {
      this.collections.clear();
      collectionGridAdapter.notifyDataSetChanged();
    }
    if (collectionPresenter != null) {
      showLoading();
      collectionPresenter.getFirstPage(query);
    }
  }

  @Override
  public void showLoading() {
    loading.show();
  }

  @Override
  public void hideLoading() {
    loading.hide();
  }

  @Override
  public void showNoContent() {
    empty.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideNoContent() {
    empty.setVisibility(View.GONE);
  }

  @Override
  public void showLoadingMore() {
    loadingMore.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoadingMore() {
    loadingMore.setVisibility(View.GONE);
  }

  @Override
  public void updateData(List<UnsplashCollection> collections) {
    this.collections.clear();
    if (collections != null && collections.size() > 0) {
      hideNoContent();
      this.collections.addAll(collections);
      collectionGridAdapter.notifyDataSetChanged();
      recyclerView.scrollVerticallyToPosition(0);
    } else {
      collectionGridAdapter.notifyDataSetChanged();
      showNoContent();
    }
    hideLoading();
  }

  @Override
  public void addData(List<UnsplashCollection> collections) {
    hideLoadingMore();
    this.collections.addAll(collections);
    collectionGridAdapter.notifyDataSetChanged();
  }
}

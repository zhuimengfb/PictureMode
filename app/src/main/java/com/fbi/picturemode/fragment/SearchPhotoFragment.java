package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailPictureActivity;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.SearchPhotoView;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.SearchPhotoPresenter;
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

public class SearchPhotoFragment extends BaseFragment implements SearchPhotoView, MyCollectView {

  @BindView(R.id.ultimate_recycler_view) UltimateRecyclerView recyclerView;
  @BindView(R.id.empty_layout) RelativeLayout empty;
  @BindView(R.id.loading_layout) RelativeLayout loadingMore;
  @BindView(R.id.loading) AVLoadingIndicatorView loading;
  private SearchPhotoPresenter searchPhotoPresenter;
  private MyCollectPresenter collectPresenter;
  private List<UnsplashPicture> pictures = new ArrayList<>();
  private NewPictureRecyclerAdapter pictureRecyclerAdapter;
  private String keyword;

  public static SearchPhotoFragment newInstance() {

    Bundle args = new Bundle();

    SearchPhotoFragment fragment = new SearchPhotoFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search_photo, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initEvent() {
    recyclerView.reenableLoadmore();
    recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
      @Override
      public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        showLoadingMore();
        searchPhotoPresenter.getNextPage(keyword);
      }
    });
    pictureRecyclerAdapter.addOnItemClickListener(new NewPictureRecyclerAdapter
        .OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        new NetworkUtils().isNetworkAvailable(view);
        DetailPictureActivity.toThisActivity(getActivity(), pictures.get(position));
      }
    });
    pictureRecyclerAdapter.addOnItemCollectListener(new NewPictureRecyclerAdapter
        .OnItemCollectListener() {
      @Override
      public void onItemCollect(int position) {
        collectPresenter.collectPicture(pictures.get(position).getId());
      }
    });
  }

  private void initData() {
    pictureRecyclerAdapter = new NewPictureRecyclerAdapter(getActivity(), pictures);
    recyclerView.setAdapter(pictureRecyclerAdapter);
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setItemAnimator(new FadeInAnimator());
  }

  @Override
  public void initPresenter() {
    searchPhotoPresenter = new SearchPhotoPresenter(this);
    collectPresenter = new MyCollectPresenter(this);
  }

  @Override
  public void destroyPresenter() {

  }

  public void query(String query) {
    keyword = query;
    if (this.pictures.size() > 0) {
      this.pictures.clear();
      pictureRecyclerAdapter.notifyDataSetChanged();
    }
    if (searchPhotoPresenter != null) {
      showLoading();
      searchPhotoPresenter.getFirstPage(keyword);
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
  public void updateData(List<UnsplashPicture> pictures) {
    this.pictures.clear();
    if (pictures != null && pictures.size() > 0) {
      hideNoContent();
      this.pictures.addAll(pictures);
      pictureRecyclerAdapter.notifyDataSetChanged();
      recyclerView.scrollVerticallyToPosition(0);
    } else {
      pictureRecyclerAdapter.notifyDataSetChanged();
      showNoContent();
    }
    hideLoading();
  }

  @Override
  public void addData(List<UnsplashPicture> pictures) {
    this.pictures.addAll(pictures);
    pictureRecyclerAdapter.notifyDataSetChanged();
    hideLoadingMore();
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

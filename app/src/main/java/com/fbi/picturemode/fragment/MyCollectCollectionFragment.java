package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailCollectionActivity;
import com.fbi.picturemode.adapter.CollectionGridAdapter;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.fragment.views.QueryCollectCollectionView;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.QueryCollectCollectionsPresenter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class MyCollectCollectionFragment extends BaseFragment implements MyCollectView,
    QueryCollectCollectionView {

  @BindView(R.id.empty_layout) RelativeLayout emptyLayout;
  @BindView(R.id.my_collect_collection_recycler_view) UltimateRecyclerView recyclerView;

  private CollectionGridAdapter adapter;
  private List<UnsplashCollection> unsplashCollections = new ArrayList<>();
  private MyCollectPresenter presenter;
  private QueryCollectCollectionsPresenter queryPresenter;

  public static MyCollectCollectionFragment newInstance() {
    return new MyCollectCollectionFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_collect_collections,
        container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;

  }

  private void initEvent() {
    adapter.addOnItemDeleteListener(new CollectionGridAdapter.OnItemDeleteListener() {
      @Override
      public void onItemDelete(int position) {
        presenter.deleteCollect("" + unsplashCollections.get(position).getId(), position);
      }
    });
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        DetailCollectionActivity.toThisActivity(getActivity(), unsplashCollections.get(position));
      }
    });
  }

  private void initData() {
    adapter = new CollectionGridAdapter(unsplashCollections);
    recyclerView.setAdapter(adapter);
    queryPresenter.queryCollectCollections();
  }

  private void initView() {

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new ScaleInAnimator());
  }

  @Override
  public void initPresenter() {
    presenter = new MyCollectPresenter(this);
    queryPresenter = new QueryCollectCollectionsPresenter(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showCollectAlready() {

  }

  @Override
  public void showCollectSuccess() {

  }

  @Override
  public void showDeleteSuccess(int position) {
    Snackbar.make(recyclerView, R.string.delete_success, Snackbar.LENGTH_SHORT).show();
    unsplashCollections.remove(position);
    adapter.notifyDataSetChanged();
    if (unsplashCollections.size() == 0) {
      showNoCollectPictures();
    }
  }

  @Override
  public void queryCollectCollections(List<UnsplashCollection> collections) {
    emptyLayout.setVisibility(View.GONE);
    this.unsplashCollections.clear();
    this.unsplashCollections.addAll(collections);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showNoCollectPictures() {
    emptyLayout.setVisibility(View.VISIBLE);
  }

  public void setMode(int mode) {
    if (adapter != null) {
      adapter.setCurrentMode(mode);
    }
  }
}

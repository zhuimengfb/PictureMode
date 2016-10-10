package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailCollectionActivity;
import com.fbi.picturemode.adapter.CollectionGridAdapter;
import com.fbi.picturemode.adapter.OnRecyclerItemClickListener;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.fragment.views.QueryCollectCollectionView;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.QueryCollectCollectionsPresenter;
import com.fbi.picturemode.utils.Constants;

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
  @BindView(R.id.my_collect_collection_recycler_view) RecyclerView recyclerView;

  private CollectionGridAdapter adapter;
  private List<UnsplashCollection> unsplashCollections = new ArrayList<>();
  private MyCollectPresenter presenter;
  private QueryCollectCollectionsPresenter queryPresenter;
  private int currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;

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
    recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
      @Override
      public void onItemClick(RecyclerView.ViewHolder vh) {
        int position = vh.getAdapterPosition();
        if (currentMode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          DetailCollectionActivity.toThisActivity(getActivity(), unsplashCollections.get(position));
        } else {
          presenter.deleteCollect("" + unsplashCollections.get(position).getId(), position);
        }
      }

      @Override
      public void onLongClick(RecyclerView.ViewHolder vh) {

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
  public void destroyPresenter() {
    presenter.onDestroy();
    queryPresenter.onDestroy();
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
//    adapter.notifyDataSetChanged();
    adapter.notifyItemRemoved(position);
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
    currentMode = mode;
    if (adapter != null) {
      adapter.setCurrentMode(mode);
    }
  }
}

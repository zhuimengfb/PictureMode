package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.DetailPictureActivity;
import com.fbi.picturemode.adapter.NewPictureRecyclerAdapter;
import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.QueryCollectPictureView;
import com.fbi.picturemode.presenter.MyCollectPresenter;
import com.fbi.picturemode.presenter.QueryCollectPicturePresenter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class MyCollectPictureFragment extends BaseFragment implements MyCollectView,
    QueryCollectPictureView {


  @BindView(R.id.empty_layout) RelativeLayout emptyLayout;
  @BindView(R.id.my_collect_picture_recycler_view) UltimateRecyclerView recyclerView;

  private NewPictureRecyclerAdapter adapter;
  private List<UnsplashPicture> unsplashPictures = new ArrayList<>();
  private MyCollectPresenter presenter;
  private QueryCollectPicturePresenter queryPresenter;

  public static MyCollectPictureFragment newInstance() {
    return new MyCollectPictureFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_collect_pictures,
        container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;

  }

  private void initEvent() {
    adapter.addOnItemDeleteListener(new NewPictureRecyclerAdapter.OnItemDeleteListener() {
      @Override
      public void onItemDelete(int position) {
        presenter.deleteCollect(unsplashPictures.get(position).getId(), position);
      }
    });
    adapter.addOnItemClickListener(new NewPictureRecyclerAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        DetailPictureActivity.toThisActivity(getActivity(), unsplashPictures.get(position));
      }
    });
  }

  private void initData() {
    adapter = new NewPictureRecyclerAdapter(getActivity(), unsplashPictures);
    recyclerView.setAdapter(adapter);
    queryPresenter.queryCollectPictures();
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setItemAnimator(new SlideInLeftAnimator());
  }

  @Override
  public void initPresenter() {
    presenter = new MyCollectPresenter(this);
    queryPresenter = new QueryCollectPicturePresenter(this);
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
    unsplashPictures.remove(position);
    adapter.notifyDataSetChanged();
    if (unsplashPictures.size() == 0) {
      showNoCollectPictures();
    }
  }

  @Override
  public void queryCollectPictures(List<UnsplashPicture> pictures) {
    emptyLayout.setVisibility(View.GONE);
    this.unsplashPictures.clear();
    this.unsplashPictures.addAll(pictures);
    adapter.notifyDataSetChanged();
    if (unsplashPictures.size() == 0) {
      showNoCollectPictures();
    }
  }

  @Override
  public void showNoCollectPictures() {
    emptyLayout.setVisibility(View.VISIBLE);
  }

  public void setMode(int mode) {
    if (adapter != null) {
      adapter.setMode(mode);
    }
  }
}

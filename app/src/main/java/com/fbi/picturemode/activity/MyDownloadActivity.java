package com.fbi.picturemode.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.fbi.picturemode.R;
import com.fbi.picturemode.activity.views.MyDownloadView;
import com.fbi.picturemode.adapter.LocalPictureAdapter;
import com.fbi.picturemode.entity.MyDownload;
import com.fbi.picturemode.presenter.MyDownloadPresenter;
import com.fbi.picturemode.utils.Constants;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyDownloadActivity extends BaseActivity implements MyDownloadView {

  @BindView(R.id.empty_layout) RelativeLayout emptyLayout;
  @BindView(R.id.download_recycler_view) UltimateRecyclerView recyclerView;
  private int currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;
  private LocalPictureAdapter adapter;
  private List<MyDownload> myDownloads = new ArrayList<>();
  private MyDownloadPresenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getString(R.string.my_download));
    enableDisplayHomeAsUp();
    initView();
    initData();
    initEvent();
  }

  private void initView() {
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
        .VERTICAL));
    recyclerView.setItemAnimator(new FadeInAnimator());
  }

  private void initData() {
    adapter = new LocalPictureAdapter(myDownloads);
    recyclerView.setAdapter(adapter);
    presenter.getDownloadList();
  }

  private void initEvent() {
    adapter.addOnItemClickListener(new LocalPictureAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        FullPictureActivity.toThisActivityFromLocal(MyDownloadActivity.this, myDownloads.get
            (position));
      }
    });
    adapter.addOnItemDeleteListener(new LocalPictureAdapter.OnItemDeleteListener() {
      @Override
      public void onItemDelete(int position) {
        presenter.deleteDownloadPicture(myDownloads.get(position), position);
      }
    });
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_my_download);

  }

  @Override
  public void initPresenter() {
    presenter = new MyDownloadPresenter(this);
  }

  @Override
  public void destroyPresenter() {
    presenter.onDestroy();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_manage:
        if (currentMode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          item.setIcon(R.drawable.manage_success);
          currentMode = Constants.MANAGE_COLLECT_MODE_DELETE;
          setMode(currentMode);
        } else if (currentMode == Constants.MANAGE_COLLECT_MODE_DELETE) {
          item.setIcon(R.drawable.manage_mode);
          currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;
          setMode(currentMode);
        }
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setMode(int mode) {
    if (adapter != null) {
      adapter.setMode(mode);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.manage_collect_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void hideNoDownloads() {
    emptyLayout.setVisibility(View.GONE);
  }

  @Override
  public void showNoDownload() {
    emptyLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void showMyDownloadList(List<MyDownload> myDownloads) {
    this.myDownloads.clear();
    this.myDownloads.addAll(myDownloads);
    adapter.notifyDataSetChanged();
    Log.d("size", "showMyDownloadList: " + myDownloads.size());
  }

  @Override
  public void showDeleteSuccess(int position) {
    Snackbar.make(recyclerView, R.string.delete_success, Snackbar.LENGTH_SHORT).show();
    adapter.notifyItemRemoved(position);
    myDownloads.remove(position);
//    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }
}

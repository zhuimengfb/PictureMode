package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fbi.picturemode.R;
import com.fbi.picturemode.adapter.AboutItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class AboutActivity extends BaseActivity {

  @BindView(R.id.about_recycler_view) RecyclerView recyclerView;
  private AboutItemAdapter aboutItemAdapter;
  private List<String> contents = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getString(R.string.about_app));
    enableDisplayHomeAsUp();
    initView();
    initData();
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new FadeInAnimator());
  }

  private void initData() {
    contents.add(getString(R.string.about_first));
    contents.add(getString(R.string.about_second));
    contents.add(getString(R.string.about_third));
    contents.add(getString(R.string.about_fourth));
    contents.add(getString(R.string.about_fifth_content));
    aboutItemAdapter = new AboutItemAdapter(contents);
    recyclerView.setAdapter(aboutItemAdapter);
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_about);
  }

  @Override
  public void initPresenter() {

  }

  @Override
  public void destroyPresenter() {

  }

  public static void toThisActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, AboutActivity.class);
    context.startActivity(intent);
  }
}

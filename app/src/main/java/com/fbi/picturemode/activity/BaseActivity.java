package com.fbi.picturemode.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fbi.picturemode.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public abstract class BaseActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) @Nullable Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCustomLayout();
    ButterKnife.bind(this);
    initToolbar();
    initPresenter();
  }

  public void initToolbar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }
  }

  public void setTitle(String title) {
    toolbar.setTitle(title);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(title);
    }
  }

  public void enableDisplayHomeAsUp() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onBackPressed();
        }
      });
    }
  }

  public Toolbar getToolbar() {
    return this.toolbar;
  }

  public abstract void setCustomLayout();

  public abstract void initPresenter();

  public abstract void destroyPresenter();
  @Override
  protected void onDestroy() {
    super.onDestroy();
    destroyPresenter();
  }
}

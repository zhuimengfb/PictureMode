package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.adapter.SettingItemAdapter;
import com.fbi.picturemode.entity.SettingItem;
import com.fbi.picturemode.utils.FileUtils;
import com.fbi.picturemode.utils.VersionUtils;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class SettingActivity extends BaseActivity {

  public static final int ITEM_ID_BLANK = 0;
  public static final int ITEM_ID_CLEAR_CACHE = 1;
  public static final int ITEM_ID_SHOW_PIC_PATH = 2;
  public static final int ITEM_ID_SHOW_PIC_SIZE = 3;
  public static final int ITEM_ID_SHOW_VERSION = 4;
  public static final int ITEM_ID_ABOUT_APP = 5;
  public static final int ITEM_ID_DOWNLOAD_LARGE_PIC = 6;


  @BindView(R.id.setting_recycler_view) RecyclerView recyclerView;
  private List<SettingItem> settingItemList = new ArrayList<>();
  private SettingItemAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getString(R.string.action_settings));
    enableDisplayHomeAsUp();
    initView();
    initData();
    initEvent();
  }

  private void initEvent() {
    adapter.addOnCheckedChangeListener(new SettingItemAdapter.OnCheckedChangeListener() {
      @Override
      public void onCheckedChange(int itemId, boolean checked) {
        switch (itemId) {
          case ITEM_ID_DOWNLOAD_LARGE_PIC:
            UserSharedPreferences.getInstance(MyApp.getContext())
                .updateDownloadHighQualityPictureUsingMobileNetwork(checked);
            break;
        }
      }
    });
    adapter.addOnItemClickListener(new SettingItemAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int itemId) {
        switch (itemId) {
          case ITEM_ID_CLEAR_CACHE:
            clearGlideCache();
            break;
          case ITEM_ID_SHOW_VERSION:
            break;
          case ITEM_ID_ABOUT_APP:
            AboutActivity.toThisActivity(SettingActivity.this);
            break;
        }
      }
    });
  }

  private void clearGlideCache() {
    Observable.just(Glide.getPhotoCacheDir(this).getAbsolutePath())
        .doOnNext(new Action1<String>() {
          @Override
          public void call(String s) {
            Glide.get(MyApp.getContext()).clearDiskCache();
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {
            Snackbar.make(recyclerView, R.string.clear_cache_success, Snackbar.LENGTH_SHORT).show();
            getMemoryCacheSize();
          }
        });
  }

  private void initData() {
    settingItemList.add(new SettingItem(ITEM_ID_BLANK, "", "", "", false, false, SettingItem
        .TYPE_BLANK, false));
    settingItemList.add(new SettingItem(ITEM_ID_DOWNLOAD_LARGE_PIC, getString(R.string
        .download_large_pic_by_mobile), "", "", false, false, SettingItem.TYPE_SWITCH,
        UserSharedPreferences.getInstance(MyApp.getContext())
            .getDownloadHighQualityPictureUsingMobileNetwork()));
    settingItemList.add(new SettingItem(ITEM_ID_BLANK, "", "", "", false, false, SettingItem
        .TYPE_BLANK, false));
    settingItemList.add(new SettingItem(ITEM_ID_CLEAR_CACHE, getString(R.string.clear_cache), "",
        "", true, true, SettingItem.TYPE_NORMAL, false));
    settingItemList.add(new SettingItem(ITEM_ID_BLANK, "", "", "", false, false, SettingItem
        .TYPE_BLANK, false));
    settingItemList.add(new SettingItem(ITEM_ID_SHOW_PIC_PATH, getString(R.string.picture_path),
        UserSharedPreferences.getInstance(MyApp.getContext()).getUserPhotoBasePath(), "", false,
        false, SettingItem.TYPE_NORMAL, false));
    settingItemList.add(new SettingItem(ITEM_ID_SHOW_PIC_SIZE, getString(R.string.picture_size),
        "", "", false, false, SettingItem.TYPE_NORMAL, false));
    settingItemList.add(new SettingItem(ITEM_ID_BLANK, "", "", "", false, false, SettingItem
        .TYPE_BLANK, false));
    settingItemList.add(new SettingItem(ITEM_ID_SHOW_VERSION, getString(R.string.app_version),
        VersionUtils.getVersionName(), "", false, true, SettingItem.TYPE_NORMAL, false));
    settingItemList.add(new SettingItem(ITEM_ID_ABOUT_APP, getString(R.string.about_app),
        "", "",false , true, SettingItem.TYPE_NORMAL, false));
    adapter = new SettingItemAdapter(settingItemList);
    recyclerView.setAdapter(adapter);
    getMemoryCacheSize();
    getDownloadPhotoSize();
  }


  private void getMemoryCacheSize() {
    Observable.just(Glide.getPhotoCacheDir(this).getAbsolutePath())
        .map(getDirSizeFunc)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {
            SettingItem settingItem = findSettingItemById(ITEM_ID_CLEAR_CACHE);
            if (settingItem != null) {
              settingItem.setRightContent(s);
              adapter.notifyDataSetChanged();
            }
          }
        });
  }

  private SettingItem findSettingItemById(int itemId) {
    for (SettingItem settingItem : settingItemList) {
      if (settingItem.getItemId() == itemId) {
        return settingItem;
      }
    }
    return null;
  }

  private Func1<String, String> getDirSizeFunc = new Func1<String, String>() {
    @Override
    public String call(String s) {
      return FileUtils.getFormatSize(FileUtils.getFolderSize(new File(s)));
    }
  };

  private void getDownloadPhotoSize() {
    Observable.just(UserSharedPreferences.getInstance(MyApp.getContext()).getUserPhotoBasePath())
        .map(getDirSizeFunc)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(String s) {
            SettingItem settingItem = findSettingItemById(ITEM_ID_SHOW_PIC_SIZE);
            if (settingItem != null) {
              settingItem.setSubTitle(s);
              adapter.notifyDataSetChanged();
            }
          }
        });
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new FadeInAnimator());
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_setting);

  }

  @Override
  public void initPresenter() {

  }

  @Override
  public void destroyPresenter() {

  }


  public static void toThisActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, SettingActivity.class);
    context.startActivity(intent);
  }
}

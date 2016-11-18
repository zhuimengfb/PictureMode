package com.fbi.picturemode.utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/7/16
 */

public class SetWallpaperUtils {

  public static final int SET_WALLPAPER_CODE = 101;

  public static void setWallpaperFromFile(File file) throws IOException {
    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApp.getContext());
    InputStream in = new FileInputStream(file);
    wallpaperManager.setStream(in);
  }

  public static void setWallpaperBySystem(final Activity activity, final String path) {
    Observable.just(path)
        .subscribeOn(Schedulers.newThread())
        .doOnNext(new Action1<String>() {
          @Override
          public void call(String s) {
            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("mimeType", "image/*");
            intent.setDataAndType(Uri.parse("file://"+path),"image/*");
            activity.startActivityForResult(Intent.createChooser(intent, MyApp.getContext()
                .getString(R.string.set_wallpaper)), SET_WALLPAPER_CODE);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onNext(String s) {

          }
        });
  }
}

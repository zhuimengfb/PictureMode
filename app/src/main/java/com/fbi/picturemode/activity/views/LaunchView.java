package com.fbi.picturemode.activity.views;

import com.fbi.picturemode.base.BaseView;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public interface LaunchView extends BaseView {

  void showPicture(String url, String color);

  void showUserIcon(String url);

  void showLocation(String location);

  void showUserName(String userName);

  void hideLoading();

  void showLoading();

  void showLocalPicture(String lastLocalPath, String lastRandomPictureLink, String lastRandomColor);

  void showDefaultPicture();

  void hideSetWallpaper();

  void showSetWallpaperSuccess();

  void showSetWallpaperFail();

  void setWallpaper(String path);
}

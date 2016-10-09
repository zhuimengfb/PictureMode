package com.fbi.picturemode.activity.views;

import com.fbi.picturemode.base.BaseView;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public interface FullPictureView extends BaseView {
  void showCollectAlready();

  void showCollectSuccess();

  void showDownloadAlready();

  void showDownloadSuccess();

  void showDownloadFail();

  void showLoading();

  void stopLoading();

  void showSetWallpaperSuccess();

  void showSetWallpaperFail();

  void showAlreadySetWallpaper();
}

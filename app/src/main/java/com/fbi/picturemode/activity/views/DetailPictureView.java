package com.fbi.picturemode.activity.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashExif;
import com.fbi.picturemode.entity.UnsplashPicture;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public interface DetailPictureView extends BaseView {
  void updatePictureInfo(UnsplashPicture unsplashPicture);

  void updateCameraInfo(UnsplashExif exifInfo);

  void updatePictureList(List<UnsplashPicture> unsplashPictures);

  void updateFirstPageList(List<UnsplashPicture> unsplashPictures);

  void startLoadingMore();

  void loadingComplete();
}

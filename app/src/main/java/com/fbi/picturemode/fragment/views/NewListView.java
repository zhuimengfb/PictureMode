package com.fbi.picturemode.fragment.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashPicture;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public interface NewListView extends BaseView {

  void updatePictureList(List<UnsplashPicture> unsplashPictures);

  void updateFirstPageList(List<UnsplashPicture> unsplashPictures);

  void showRefreshing();

  void stopRefreshing();

  void startLoadingMore();

  void loadingComplete();
}

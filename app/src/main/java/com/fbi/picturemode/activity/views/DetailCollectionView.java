package com.fbi.picturemode.activity.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public interface DetailCollectionView extends BaseView {

  void updatePictureList(List<UnsplashPicture> unsplashPictures);

  void updateFirstPageList(List<UnsplashPicture> unsplashPictures);

  void startLoadingMore();

  void loadingComplete();

  void updateCollectionInfo(UnsplashCollection collection);

  void updateRelatedCollections(List<UnsplashCollection> collections);

  void showNoRelatedCollections();
}

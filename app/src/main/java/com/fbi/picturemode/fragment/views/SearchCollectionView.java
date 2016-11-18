package com.fbi.picturemode.fragment.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashCollection;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 18/10/2016
 */

public interface SearchCollectionView extends BaseView {

  void showLoading();

  void hideLoading();

  void showNoContent();

  void hideNoContent();

  void showLoadingMore();

  void hideLoadingMore();

  void updateData(List<UnsplashCollection> collections);

  void addData(List<UnsplashCollection> collections);
}

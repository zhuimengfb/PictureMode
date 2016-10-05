package com.fbi.picturemode.fragment.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashCollection;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public interface CollectionsView extends BaseView {

  void updateFirstPageCollections(List<UnsplashCollection> collections);

  void updateNextPageCollections(List<UnsplashCollection> collections);

  void showRefreshing();

  void hideRefreshing();

  void showLoadMore();

  void loadComplete();

}

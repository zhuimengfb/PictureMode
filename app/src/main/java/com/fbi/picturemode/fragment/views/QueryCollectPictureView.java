package com.fbi.picturemode.fragment.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.UnsplashPicture;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public interface QueryCollectPictureView extends BaseView {

  void queryCollectPictures(List<UnsplashPicture> pictures);

  void showNoCollectPictures();
}

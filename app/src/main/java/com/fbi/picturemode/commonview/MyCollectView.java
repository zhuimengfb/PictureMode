package com.fbi.picturemode.commonview;

import com.fbi.picturemode.base.BaseView;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public interface MyCollectView extends BaseView {

  void showCollectAlready();

  void showCollectSuccess();

  void showDeleteSuccess(int position);
}

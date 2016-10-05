package com.fbi.picturemode.activity.views;

import com.fbi.picturemode.base.BaseView;
import com.fbi.picturemode.entity.MyDownload;

import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public interface MyDownloadView extends BaseView {

  void hideNoDownloads();

  void showNoDownload();

  void showMyDownloadList(List<MyDownload> myDownloads);

  void showDeleteSuccess(int position);
}

package com.fbi.picturemode.presenter;

import com.fbi.picturemode.activity.views.MyDownloadView;
import com.fbi.picturemode.entity.MyDownload;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class MyDownloadPresenter extends BasePresenter<MyDownloadView> {

  private UnsplashModel model;

  public MyDownloadPresenter(MyDownloadView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  @Override
  protected void destroyModel() {
    model = null;
  }

  public void getDownloadList() {
    getSubscriptions().add(model.queryDownloadList(new Subscriber<List<MyDownload>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<MyDownload> myDownloads) {
        if (myDownloads == null || myDownloads.size() == 0) {
          getView().showNoDownload();
        } else {
          getView().hideNoDownloads();
          getView().showMyDownloadList(myDownloads);
        }
      }
    }));
  }

  public void deleteDownloadPicture(MyDownload myDownload, final int position) {
    getSubscriptions().add(model.deleteDownloadPicture(myDownload, new Subscriber<Integer>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(Integer integer) {
        getView().showDeleteSuccess(position);
      }
    }));
  }
}

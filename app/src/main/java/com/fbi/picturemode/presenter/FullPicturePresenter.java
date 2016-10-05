package com.fbi.picturemode.presenter;

import com.fbi.picturemode.activity.views.FullPictureView;
import com.fbi.picturemode.db.contract.MyCollectContract;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.UnsplashModel;
import com.fbi.picturemode.utils.Constants;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class FullPicturePresenter extends BasePresenter<FullPictureView> {

  private UnsplashModel model;

  public FullPicturePresenter(FullPictureView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  public void collectPicture(String id) {
    getSubscriptions().add(model.collect(id, MyCollectContract.TYPE_PICTURE, new
        Subscriber<Integer>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onNext(Integer integer) {
            if (integer == MyCollectContract.CODE_INSERT_ALREADY) {
              getView().showCollectAlready();
            } else if (integer == MyCollectContract.CODE_INSERT_SUCCESS) {
              getView().showCollectSuccess();
            }
          }
        }));
  }

  public void downloadPicture(UnsplashPicture unsplashPicture) {
    getView().showLoading();
    getSubscriptions().add(model.downloadPicture(unsplashPicture.getUnsplashPictureLinks()
        .getFull(), unsplashPicture.getId(), new Subscriber<Integer>() {

      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer integer) {
        getView().stopLoading();
        switch (integer) {
          case Constants.CODE_PICTURE_DOWNLOAD_ALREADY:
            getView().showDownloadAlready();
            break;
          case Constants.CODE_PICTURE_DOWNLOAD_SUCCESS:
            getView().showDownloadSuccess();
            break;
          case Constants.CODE_PICTURE_DOWNLOAD_FAIL:
            getView().showDownloadFail();
            break;
        }
      }
    }));
  }
}

package com.fbi.picturemode.presenter;

import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.QueryCollectPictureView;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class QueryCollectPicturePresenter extends BasePresenter<QueryCollectPictureView> {
  private UnsplashModel model;

  public QueryCollectPicturePresenter(QueryCollectPictureView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  public void queryCollectPictures() {
    getSubscriptions().add(model.queryCollectPictures(new Subscriber<List<UnsplashPicture>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        getView().showNoCollectPictures();
      }

      @Override
      public void onNext(List<UnsplashPicture> pictures) {
        if (pictures == null || pictures.size() == 0) {
          getView().showNoCollectPictures();
        } else {
          getView().queryCollectPictures(pictures);
        }
      }
    }));
  }
}


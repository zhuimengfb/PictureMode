package com.fbi.picturemode.presenter;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.fragment.views.QueryCollectCollectionView;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class QueryCollectCollectionsPresenter extends BasePresenter<QueryCollectCollectionView> {


  private UnsplashModel model;

  public QueryCollectCollectionsPresenter(QueryCollectCollectionView baseView) {
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

  public void queryCollectCollections() {
    getSubscriptions().add(model.queryCollectCollections(new Subscriber<List<UnsplashCollection>>
        () {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<UnsplashCollection> collections) {
        if (collections == null || collections.size() == 0) {
          getView().showNoCollectPictures();
        } else {
          getView().queryCollectCollections(collections);
        }
      }
    }));
  }
}

package com.fbi.picturemode.presenter;

import android.util.Log;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.fragment.views.CollectionsView;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class CollectionPresenter extends BasePresenter<CollectionsView> {
  private UnsplashModel model;
  private int currentPage = 1;
  private int pageNum = 8;

  public CollectionPresenter(CollectionsView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  public void getFirstPageCollections() {
    getView().showRefreshing();
    currentPage = 1;
    getPageCollections(currentPage);
  }

  public void getNextPageCollections() {
    getView().showLoadMore();
    currentPage++;
    getPageCollections(currentPage);
  }

  private void getPageCollections(final int page) {
    getSubscriptions().add(model.getPageCollections(page, pageNum, new
        Subscriber<List<UnsplashCollection>>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getView().hideRefreshing();
            getView().loadComplete();
            getSubscriptions().add(model.getPageCollectionsFromDB(page, pageNum, new Subscriber<List<UnsplashCollection>>() {


              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onNext(List<UnsplashCollection> collections) {
                if (currentPage == 1) {
                  getView().updateFirstPageCollections(collections);
                  getView().hideRefreshing();
                } else {
                  getView().updateNextPageCollections(collections);
                  getView().loadComplete();
                }
              }
            }));
          }

          @Override
          public void onNext(List<UnsplashCollection> unsplashCollections) {
            Log.d(TAG, "onNext: " + unsplashCollections.size());
            if (currentPage == 1) {
              getView().updateFirstPageCollections(unsplashCollections);
              getView().hideRefreshing();
            } else {
              getView().updateNextPageCollections(unsplashCollections);
              getView().loadComplete();
            }
          }
        }));
  }

}

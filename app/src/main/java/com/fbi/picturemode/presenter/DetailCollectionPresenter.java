package com.fbi.picturemode.presenter;

import android.util.Log;

import com.fbi.picturemode.activity.views.DetailCollectionView;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class DetailCollectionPresenter extends BasePresenter<DetailCollectionView> {
  private UnsplashModel model;
  private int currentPage = 1;
  private int pageNum = 20;

  public DetailCollectionPresenter(DetailCollectionView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    model = null;
  }

  public void updateCollectionInfo(int id) {
    getSubscriptions().add(model.getDetailCollection(id, new Subscriber<UnsplashCollection>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(UnsplashCollection collection) {
        getView().updateCollectionInfo(collection);
      }
    }));
  }

  public void getFirstPagePictures(int id) {
    currentPage = 1;
    getPagePictures(id, currentPage);
  }

  public void getNextPagePictures(int id) {
    getView().startLoadingMore();
    currentPage++;
    getPagePictures(id, currentPage);
  }

  private void getPagePictures(int id, int page) {
    getSubscriptions().add(model.getCollectionPictures(id, page, pageNum, new
        Subscriber<List<UnsplashPicture>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getView().loadingComplete();
          }

          @Override
          public void onNext(List<UnsplashPicture> unsplashPictures) {
            Log.d(TAG, "onNext: collection picture" + unsplashPictures.size());
            if (currentPage == 1) {
              getView().updateFirstPageList(unsplashPictures);
            } else {
              getView().updatePictureList(unsplashPictures);
              getView().loadingComplete();
            }
          }
        }));
  }

  public void getRelatedCollections(final int collectionId) {
    getSubscriptions().add(model.getRelatedCollections(collectionId, new
        Subscriber<List<UnsplashCollection>>() {

      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(List<UnsplashCollection> collections) {
        if (collections.size() > 0) {
          getView().updateRelatedCollections(collections);
        } else {
          getView().showNoRelatedCollections();
        }
      }
    }));
  }
}

package com.fbi.picturemode.presenter;

import android.util.Log;

import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.fragment.views.NewListView;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class NewListPresenter extends BasePresenter<NewListView> {
  private int currentPage = 1;
  private int pageNum = 10;
  private UnsplashModel model;

  public NewListPresenter(NewListView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }


  public void getFirstPagePictures() {
    getView().showRefreshing();
    currentPage = 1;
    getPagePictures(currentPage);
  }

  public void getNextPagePictures() {
    getView().startLoadingMore();
    currentPage++;
    getPagePictures(currentPage);
  }

  private void getPagePictures(final int page) {
    getSubscriptions().add(model.getPagePictures(page, pageNum, new
        Subscriber<List<UnsplashPicture>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getView().stopRefreshing();
            getView().loadingComplete();
            getSubscriptions().add(model.getPagePictureFromDB(page, pageNum, new
                Subscriber<List<UnsplashPicture>>() {
                  @Override
                  public void onCompleted() {

                  }

                  @Override
                  public void onError(Throwable e) {

                  }

                  @Override
                  public void onNext(List<UnsplashPicture> unsplashPictures) {
                    if (currentPage == 1) {
                      getView().updateFirstPageList(unsplashPictures);
                      getView().stopRefreshing();
                    } else {
                      getView().updatePictureList(unsplashPictures);
                      getView().loadingComplete();
                    }
                  }
                }));
          }

          @Override
          public void onNext(List<UnsplashPicture> unsplashPictures) {
            Log.d(TAG, "onNext: " + unsplashPictures.size());
            if (currentPage == 1) {
              getView().updateFirstPageList(unsplashPictures);
              getView().stopRefreshing();
            } else {
              getView().updatePictureList(unsplashPictures);
              getView().loadingComplete();
            }
          }
        }));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    model = null;
  }
}

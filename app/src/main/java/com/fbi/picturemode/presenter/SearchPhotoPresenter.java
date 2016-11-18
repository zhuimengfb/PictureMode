package com.fbi.picturemode.presenter;

import com.fbi.picturemode.entity.UnsplashSearchPhoto;
import com.fbi.picturemode.fragment.views.SearchPhotoView;
import com.fbi.picturemode.model.UnsplashModel;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 18/10/2016
 */

public class SearchPhotoPresenter extends BasePresenter<SearchPhotoView> {

  private UnsplashModel model;
  private static final int START_PAGE = 1;
  private int currentPage = START_PAGE;

  public SearchPhotoPresenter(SearchPhotoView baseView) {
    super(baseView);
  }


  public void getFirstPage(String keyword) {
    currentPage = START_PAGE;
    searchPagePhotos(keyword);
  }

  public void getNextPage(String keyword) {
    currentPage++;
    searchPagePhotos(keyword);
  }

  private void searchPagePhotos(String keyword) {
    getSubscriptions().add(model.queryPhotos(keyword, currentPage, new
        Subscriber<UnsplashSearchPhoto>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            if (getView() != null) {
              getView().hideLoading();
            }
          }

          @Override
          public void onNext(UnsplashSearchPhoto unsplashSearchPhoto) {
            if (getView() != null) {
              getView().hideLoading();
              if (currentPage == START_PAGE) {
                getView().updateData(unsplashSearchPhoto.getPictures());
              } else {
                getView().addData(unsplashSearchPhoto.getPictures());
              }
            }
          }
        }));
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  @Override
  protected void destroyModel() {
    model = null;
  }
}

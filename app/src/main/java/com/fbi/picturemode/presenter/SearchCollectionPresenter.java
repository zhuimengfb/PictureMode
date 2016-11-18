package com.fbi.picturemode.presenter;

import com.fbi.picturemode.entity.UnsplashSearchCollection;
import com.fbi.picturemode.fragment.views.SearchCollectionView;
import com.fbi.picturemode.model.UnsplashModel;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 18/10/2016
 */

public class SearchCollectionPresenter extends BasePresenter<SearchCollectionView> {

  private UnsplashModel model;
  private static final int START_PAGE = 1;
  private int currentPage = START_PAGE;

  public SearchCollectionPresenter(SearchCollectionView baseView) {
    super(baseView);
  }

  public void getFirstPage(String keyword) {
    currentPage = START_PAGE;
    searchPageCollections(keyword);
  }

  public void getNextPage(String keyword) {
    currentPage++;
    searchPageCollections(keyword);
  }

  private void searchPageCollections(String keyword) {
    getSubscriptions().add(model.queryCollections(keyword, currentPage, new
        Subscriber<UnsplashSearchCollection>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            if (getView() != null) {
              getView().hideLoading();
            }
          }

          @Override
          public void onNext(UnsplashSearchCollection unsplashSearchCollection) {
            if (getView() != null) {
              getView().hideLoading();
              if (currentPage == START_PAGE) {
                getView().updateData(unsplashSearchCollection.getCollections());
              } else {
                getView().addData(unsplashSearchCollection.getCollections());
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

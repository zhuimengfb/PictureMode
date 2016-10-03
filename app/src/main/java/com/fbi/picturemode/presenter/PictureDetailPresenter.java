package com.fbi.picturemode.presenter;

import android.util.Log;

import com.fbi.picturemode.activity.views.DetailPictureView;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.UnsplashModel;

import java.util.List;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class PictureDetailPresenter extends BasePresenter<DetailPictureView> {
  private UnsplashModel model;
  private int currentPage = 1;
  private int pageNum = 10;

  public PictureDetailPresenter(DetailPictureView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  public void getDetailPicture(String id) {
    getSubscriptions().add(model.getDetailPicture(id, new Subscriber<UnsplashPicture>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(UnsplashPicture unsplashPicture) {
        getView().updatePictureInfo(unsplashPicture);
        getView().updateCameraInfo(unsplashPicture.getExifInfo());
      }
    }));
  }

  public void getFirstPagePictures(String userName) {
    currentPage = 1;
    getPagePictures(userName, currentPage);
  }

  public void getNextPagePictures(String userName) {
    getView().startLoadingMore();
    currentPage++;
    getPagePictures(userName, currentPage);
  }

  private void getPagePictures(String userName, int page) {
    getSubscriptions().add(model.getUserPictures(userName, page, pageNum, new
        Subscriber<List<UnsplashPicture>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onNext(List<UnsplashPicture> unsplashPictures) {
            Log.d(TAG, "onNext: user picture"+unsplashPictures.size());
            if (currentPage == 1) {
              getView().updateFirstPageList(unsplashPictures);
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

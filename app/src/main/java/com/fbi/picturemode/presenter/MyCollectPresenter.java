package com.fbi.picturemode.presenter;

import com.fbi.picturemode.commonview.MyCollectView;
import com.fbi.picturemode.db.contract.MyCollectContract;
import com.fbi.picturemode.model.UnsplashModel;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollectPresenter extends BasePresenter<MyCollectView> {

  private UnsplashModel model;

  public MyCollectPresenter(MyCollectView baseView) {
    super(baseView);
  }

  public void collectPicture(String id) {
    getSubscriptions().add(model.collect(id, MyCollectContract.TYPE_PICTURE, new
        Subscriber<Integer>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

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

  @Override
  protected void initModel() {
    model = new UnsplashModel();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    model = null;
  }

  public void collectCollections(int id) {
    getSubscriptions().add(model.collect("" + id, MyCollectContract.TYPE_COLLECTION, new
        Subscriber<Integer>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

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

  public void deleteCollect(String collectId,final int position) {
    getSubscriptions().add(model.deleteMyCollect(collectId, new Subscriber<String>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(String s) {
        getView().showDeleteSuccess(position);
      }
    }));
  }
}

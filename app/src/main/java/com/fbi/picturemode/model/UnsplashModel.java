package com.fbi.picturemode.model;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.apimanager.UnsplashApiManager;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class UnsplashModel extends BaseModel {


  public Subscription getRandomPicture(Subscriber<UnsplashPicture> subscriber) {
    return UnsplashApiManager.getRandomPicture()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPagePictures(int page, int pageNum, Subscriber<List<UnsplashPicture>>
      subscriber) {
    return UnsplashApiManager.getPagePictures(page, pageNum)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPageCollections(int page, int pageNum,
                                         Subscriber<List<UnsplashCollection>> subscriber) {
    return UnsplashApiManager.getPageCollections(page, pageNum)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getDetailPicture(String id, Subscriber<UnsplashPicture> subscriber) {
    return UnsplashApiManager.getDetailPicture(id)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getUserPictures(String userName, int page, int pageNum,
                                      Subscriber<List<UnsplashPicture>> subscriber) {
    return UnsplashApiManager.getUserPictures(userName, page, pageNum)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }
}

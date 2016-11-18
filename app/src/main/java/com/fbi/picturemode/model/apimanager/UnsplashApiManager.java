package com.fbi.picturemode.model.apimanager;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.entity.UnsplashSearchCollection;
import com.fbi.picturemode.entity.UnsplashSearchPhoto;
import com.fbi.picturemode.model.apiservice.UnsplashApiService;

import java.util.List;

import rx.Observable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class UnsplashApiManager extends BaseApiManager {

  private static UnsplashApiService unsplashApiService = unsplashRetrofit.create
      (UnsplashApiService.class);

  public static Observable<UnsplashPicture> getRandomPicture() {
    return unsplashApiService.getRandomPicture();
  }

  public static Observable<List<UnsplashPicture>> getPagePictures(int page, int pageNum) {
    return unsplashApiService.getPagePictures(page, pageNum, "latest");
  }

  public static Observable<List<UnsplashCollection>> getPageCollections(int page, int pageNum) {
    return unsplashApiService.getPageCollections(page, pageNum);
  }

  public static Observable<UnsplashPicture> getDetailPicture(String id) {
    return unsplashApiService.getDetailPicture(id);
  }

  public static Observable<List<UnsplashPicture>> getUserPictures(String userName, int page, int
      pageNum) {
    return unsplashApiService.getUserPictures(userName, page, pageNum);
  }

  public static Observable<List<UnsplashPicture>> getCollectionPictures(int collectionId, int
      page, int pageNum) {
    return unsplashApiService.getCollectionPictures(collectionId, page, pageNum);
  }

  public static Observable<UnsplashCollection> getDetailCollection(int collectionId) {
    return unsplashApiService.getDetailCollection(collectionId);
  }

  public static Observable<List<UnsplashCollection>> getRelatedCollections(int collectionId) {
    return unsplashApiService.getRelatedCollections(collectionId);
  }

  public static Observable<UnsplashSearchPhoto> queryPhotos(String keyWord,int page) {
    return unsplashApiService.queryPhotos(keyWord,page);
  }

  public static Observable<UnsplashSearchCollection> queryCollections(String keyWord,int page) {
    return unsplashApiService.queryCollections(keyWord,page);
  }
}

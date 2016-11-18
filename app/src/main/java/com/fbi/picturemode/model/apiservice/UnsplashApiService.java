package com.fbi.picturemode.model.apiservice;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.entity.UnsplashSearchCollection;
import com.fbi.picturemode.entity.UnsplashSearchPhoto;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public interface UnsplashApiService {

  @GET("/photos/random")
  Observable<UnsplashPicture> getRandomPicture();

  @GET("/photos")
  Observable<List<UnsplashPicture>> getPagePictures(@Query("page") int page, @Query("per_page")
      int pageNum, @Query("order_by") String order);

  @GET("/collections")
  Observable<List<UnsplashCollection>> getPageCollections(@Query("page") int page, @Query
      ("per_page") int pageNum);

  @GET("/photos/{id}")
  Observable<UnsplashPicture> getDetailPicture(@Path("id") String id);

  @GET("/users/{userName}/photos")
  Observable<List<UnsplashPicture>> getUserPictures(@Path("userName") String userName, @Query
      ("page") int page, @Query("per_page") int pageNum);

  @GET("/collections/{id}/photos")
  Observable<List<UnsplashPicture>> getCollectionPictures(@Path("id") int collectionId, @Query
      ("page") int page, @Query("per_page") int pageNum);

  @GET("/collections/{id}")
  Observable<UnsplashCollection> getDetailCollection(@Path("id") int collectionId);

  @GET("/collections/{id}/related")
  Observable<List<UnsplashCollection>> getRelatedCollections(@Path("id") int collectionId);

  @GET("/search/photos")
  Observable<UnsplashSearchPhoto> queryPhotos(@Query("query") String keyWord,@Query("page") int page);

  @GET("/search/collections")
  Observable<UnsplashSearchCollection> queryCollections(@Query("query") String keyWord, @Query
      ("page") int page);
}

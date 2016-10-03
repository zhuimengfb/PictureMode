package com.fbi.picturemode.model.apiservice;

import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;

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
}

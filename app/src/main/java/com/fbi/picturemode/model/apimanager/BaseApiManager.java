package com.fbi.picturemode.model.apimanager;

import com.fbi.picturemode.config.GlobalConfig;
import com.fbi.picturemode.utils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class BaseApiManager {

  private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request()
          .newBuilder()
          .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
          .addHeader("Connection", "keep-alive")
          .addHeader("Accept", "*/*")
          .addHeader("Cookie", "add cookies here")
          .addHeader("Authorization", GlobalConfig.getOauthTypeHeader())
          .build();
      return chain.proceed(request);
    }
  }).build();

  protected static Retrofit unsplashRetrofit = new Retrofit.Builder().baseUrl(Constants
      .UNSPLASH_SERVER_URL).client(okHttpClient).addConverterFactory(JacksonConverterFactory
      .create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

}

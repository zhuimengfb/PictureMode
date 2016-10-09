package com.fbi.picturemode.utils;

import com.fbi.picturemode.MyApp;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class Constants {

  public static final String UNSPLASH_SERVER_URL = "https://api.unsplash.com";
  public static final String UNSPLASH_CLIENT_ID =
      "c993023fb6928fc21d1986926f8ae03695fb7bd39766cdea129cf545d585250e";
  public static final String BUGLY_APP_ID = "c61c4d9906";

  public static final int MANAGE_COLLECT_MODE_NORMAL = 0;
  public static final int MANAGE_COLLECT_MODE_DELETE = 1;

  public static final String BASE_PHOTO_PATH = MyApp.getContext().getExternalFilesDir("photos")
      .getAbsolutePath();
  public static final String BASE_PHOTO_TEMP_PATH = MyApp.getContext().getExternalFilesDir
      ("temp").getAbsolutePath();

  public static final String CODE_PICTURE_DOWNLOAD_ALREADY = "0";
  public static final String CODE_PICTURE_DOWNLOAD_FAIL = "2";
  public static final int DELETE_DOWNLOAD_SUCCESS = 1;
}

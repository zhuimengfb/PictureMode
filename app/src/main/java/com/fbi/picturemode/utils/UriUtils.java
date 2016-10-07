package com.fbi.picturemode.utils;

import android.content.ContentResolver;
import android.net.Uri;

import com.fbi.picturemode.MyApp;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/6/16
 */

public class UriUtils {

  public static Uri getDrawableUri(int drawableId) {
    return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
        + MyApp.getContext().getResources().getResourcePackageName(drawableId) + "/"
        + MyApp.getContext().getResources().getResourceTypeName(drawableId) + "/"
        + MyApp.getContext().getResources().getResourceEntryName(drawableId));
  }
}

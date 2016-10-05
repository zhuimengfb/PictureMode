package com.fbi.picturemode.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.fbi.picturemode.utils.Constants;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class UserSharedPreferences {
  private static final String KEY_PREFERENCES = "user_preferences";
  private static final String KEY_USER_ACCESS_TOKEN = "user_access_token";
  private static final String KEY_USER_PHOTO_BASE_PATH = "user_photo_base_path";
  private static final String KEY_DOWNLOAD_HIGH_QUALITY_PIC_USING_MOBILE_NETOWRK =
      "download_high_quality_using_mobile";
  private static UserSharedPreferences userSharedPreferences;
  private static SharedPreferences sharedPreferences;

  private UserSharedPreferences() {
  }

  public static UserSharedPreferences getInstance(Context context) {
    if (userSharedPreferences == null) {
      synchronized (UserSharedPreferences.class) {
        if (userSharedPreferences == null) {
          userSharedPreferences = new UserSharedPreferences();
          sharedPreferences = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        }
      }
    }
    return userSharedPreferences;
  }

  public String getAccessToken() {
    return sharedPreferences.getString(KEY_USER_ACCESS_TOKEN, "");
  }

  public void updateUserPhotoBasePath(String path) {
    sharedPreferences.edit().putString(KEY_USER_PHOTO_BASE_PATH, path).apply();
  }

  public String getUserPhotoBasePath() {
    return sharedPreferences.getString(KEY_USER_PHOTO_BASE_PATH, Constants.BASE_PHOTO_PATH);
  }

  public boolean getDownloadHighQualityPictureUsingMobileNetwork() {
    return sharedPreferences.getBoolean(KEY_DOWNLOAD_HIGH_QUALITY_PIC_USING_MOBILE_NETOWRK, true);
  }

  public void updateDownloadHighQualityPictureUsingMobileNetwork(boolean use) {
    sharedPreferences.edit().putBoolean(KEY_DOWNLOAD_HIGH_QUALITY_PIC_USING_MOBILE_NETOWRK, use)
        .apply();
  }
}

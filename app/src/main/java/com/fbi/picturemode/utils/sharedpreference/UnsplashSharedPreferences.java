package com.fbi.picturemode.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class UnsplashSharedPreferences {

  private static final String KEY_PREFERENCES = "unsplash_preferences";
  private static final String KEY_LAST_RANDOM_PICTURE_LINK = "last_random_picture_link";
  private static final String KEY_LAST_RANDOM_USER_LINK = "last_random_user_link";
  private static final String KEY_LAST_RANDOM_USER_NAME = "last_random_user_name";
  private static final String KEY_LAST_RANDOM_LOCATION = "last_random_location";
  private static final String KEY_LAST_RANDOM_COLOR = "last_random_color";
  private static UnsplashSharedPreferences unsplashPreferences;
  private static SharedPreferences sharedPreferences;

  private UnsplashSharedPreferences() {
  }

  public static UnsplashSharedPreferences getInstance(Context context) {
    if (unsplashPreferences == null) {
      synchronized (UnsplashSharedPreferences.class) {
        if (unsplashPreferences == null) {
          unsplashPreferences = new UnsplashSharedPreferences();
          sharedPreferences = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        }
      }
    }
    return unsplashPreferences;
  }

  public String getLastRandomPictureLink() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_PICTURE_LINK, "");
  }

  public void updateLastRandomPictureLink(String lastRandomOictureLink) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_PICTURE_LINK, lastRandomOictureLink).apply();
  }

  public void updateLastRandomUserLink(String link) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_USER_LINK,link).apply();
  }

  public String getLastRandomUserLink() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_USER_LINK, "");
  }

  public void updateLastRandomUserName(String userName) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_USER_NAME, userName).apply();
  }
  public String getLastRandomUserName() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_USER_NAME, "");
  }

  public void updateLastRandomLocation(String location) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_LOCATION, location).apply();
  }

  public String getLastRandomLocation() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_LOCATION, "");
  }

  public void updateLastRandomColor(String color) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_COLOR, color).apply();
  }
  public String getLastRandomColor() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_COLOR, "#FFFFFF");
  }
}

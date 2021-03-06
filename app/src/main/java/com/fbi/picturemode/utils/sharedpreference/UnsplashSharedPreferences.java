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
  private static final String KEY_LAST_RANDOM_PICTURE_LINK_REGULAR = "last_random_picture_link_regular";
  private static final String KEY_LAST_RANDOM_USER_LINK = "last_random_user_link";
  private static final String KEY_LAST_RANDOM_USER_NAME = "last_random_user_name";
  private static final String KEY_LAST_RANDOM_LOCATION = "last_random_location";
  private static final String KEY_LAST_RANDOM_COLOR = "last_random_color";
  private static final String KEY_LAST_LOCAL_PATH = "LAST_LOCAL_PATH";
  private static final String KEY_CLIENT_ID_INDEX="key_client_id_index";
  private static final String KEY_LAST_PICTURE_ID = "last_picture_id";
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

  public String getKeyLastRandomPictureLinkRegular() {
    return sharedPreferences.getString(KEY_LAST_RANDOM_PICTURE_LINK_REGULAR, "");
  }

  public void updateLastRandomPictureLinkRegular(String link) {
    sharedPreferences.edit().putString(KEY_LAST_RANDOM_PICTURE_LINK_REGULAR, link).apply();
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
  public String getLastLocalPath(){
    return sharedPreferences.getString(KEY_LAST_LOCAL_PATH, "");
  }

  public void updateLastLocalPath(String path) {
    sharedPreferences.edit().putString(KEY_LAST_LOCAL_PATH, path).apply();
  }

  public int getClientIdIndex() {
    return sharedPreferences.getInt(KEY_CLIENT_ID_INDEX, 0);
  }

  public void updateClientIdIndex(int index) {
    sharedPreferences.edit().putInt(KEY_CLIENT_ID_INDEX, index).apply();
  }

  public void updateLastPictureId(String id) {
    sharedPreferences.edit().putString(KEY_LAST_PICTURE_ID, id).apply();
  }

  public String getLastPictureId() {
    return sharedPreferences.getString(KEY_LAST_PICTURE_ID, "");
  }
}

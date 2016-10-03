package com.fbi.picturemode.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class UserSharedPreferences {
  private static final String KEY_PREFERENCES = "user_preferences";
  private static final String KEY_USER_ACCESS_TOKEN = "user_access_token";
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
}

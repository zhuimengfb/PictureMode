package com.fbi.picturemode.config;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.utils.Constants;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class GlobalConfig {

  public static final int OAUTH_TYPE_PUBLIC = 0;
  private static final String OAUTH_PUBLIC_HEADER = "Client-ID ";
  public static final int OAUTH_TYPE_USER = 1;
  private static final String OAUTH_USER_HEADER = "Bearer ";
  private static int oauthType = OAUTH_TYPE_PUBLIC;

  public void setOauthType(int type) {
    oauthType = type;
  }

  public static String getOauthTypeHeader() {
    switch (oauthType) {
      case OAUTH_TYPE_PUBLIC:
        return OAUTH_PUBLIC_HEADER + Constants.UNSPLASH_CLIENT_ID;
      case OAUTH_TYPE_USER:
        return OAUTH_USER_HEADER + UserSharedPreferences.getInstance(MyApp.getContext())
            .getAccessToken();
    }
    return OAUTH_PUBLIC_HEADER;
  }

}

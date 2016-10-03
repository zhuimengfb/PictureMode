package com.fbi.picturemode;

import android.app.Application;
import android.content.Context;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class MyApp extends Application{

  private static Context context;


  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }



  public static Context getContext() {
    return context;
  }
}

package com.fbi.picturemode.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/8/16
 */

public class StatusBarUtils {


  @TargetApi(19)
  public static void setTranslucentStatus(Activity activity, boolean on) {
    Window win = activity.getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }
}

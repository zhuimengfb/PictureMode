package com.fbi.picturemode.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.picturemode.MyApp;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class VersionUtils {

  public static String getVersionName() {
    // 获取packagemanager的实例
    PackageManager packageManager = MyApp.getContext().getPackageManager();
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    PackageInfo packInfo = null;
    try {
      packInfo = packageManager.getPackageInfo(MyApp.getContext().getPackageName(), 0);
      String version = packInfo.versionName;
      return version;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }
}

package com.fbi.picturemode.utils;

import android.app.WallpaperManager;

import com.fbi.picturemode.MyApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/7/16
 */

public class SetWallpaperUtils {

  public static void setWallpaperFromFile(File file) throws IOException {
    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MyApp.getContext());
    InputStream in = new FileInputStream(file);
    wallpaperManager.setStream(in);
  }
}

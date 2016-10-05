package com.fbi.picturemode.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class NetworkUtils {


  public static boolean isNetworkReachable() {
    ConnectivityManager cm = (ConnectivityManager) MyApp.getContext().getSystemService
        (Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    return ni != null && ni.isConnectedOrConnecting();
  }

  public boolean isNetworkAvailable(View view) {
    if (isNetworkReachable()) {
      return true;
    } else {
      Snackbar.make(view, MyApp.getContext().getString(R.string.please_check_network), Snackbar
          .LENGTH_SHORT).show();
      return false;
    }
  }

  public static int getNetworkType() {
    ConnectivityManager cm = (ConnectivityManager) MyApp.getContext().getSystemService
        (Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    if (ni != null) {
      return ni.getType();
    } else {
      return -1;
    }
  }
}

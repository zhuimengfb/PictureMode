package com.fbi.picturemode.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class DateUtils {
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public static String formatDate(Date date) {
    return simpleDateFormat.format(date);
  }
}

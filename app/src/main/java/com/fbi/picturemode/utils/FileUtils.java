package com.fbi.picturemode.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class FileUtils {

  public static void copyFileUsingFileChannels(File source, File dest)
      throws IOException {
    FileChannel inputChannel = null;
    FileChannel outputChannel = null;
    try {
      inputChannel = new FileInputStream(source).getChannel();
      outputChannel = new FileOutputStream(dest).getChannel();
      outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
      inputChannel.close();
      outputChannel.close();
    }
  }

  public static long getFolderSize(File file) {
    long size = 0;
    try {
      File[] fileList = file.listFiles();
      for (File aFileList : fileList) {
        if (aFileList.isDirectory()) {
          size = size + getFolderSize(aFileList);
        } else {
          size = size + aFileList.length();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return size;
  }

  public static String getFormatSize(double size) {

    double kiloByte = size / 1024;
    if (kiloByte < 1) {
      return size + "Byte";
    }

    double megaByte = kiloByte / 1024;
    if (megaByte < 1) {
      BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
      return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
    }

    double gigaByte = megaByte / 1024;
    if (gigaByte < 1) {
      BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
      return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
    }

    double teraBytes = gigaByte / 1024;
    if (teraBytes < 1) {
      BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
      return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
    }
    BigDecimal result4 = new BigDecimal(teraBytes);

    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
  }
}

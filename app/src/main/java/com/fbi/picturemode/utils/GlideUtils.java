package com.fbi.picturemode.utils;

import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fbi.picturemode.MyApp;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class GlideUtils {


  public static void showPicture(ImageView imageView, String pictureUrl, String
      pictureBackgroundColor) {
    if (imageView != null) {
      imageView.setBackgroundColor(Color.parseColor(pictureBackgroundColor));
      Glide.with(MyApp.getContext()).load(Uri.parse(pictureUrl)).into(imageView);
    }
  }

  public static void showUserIcon(ImageView imageView, String url) {
    if (imageView != null) {
      Glide.with(MyApp.getContext()).load(Uri.parse(url)).bitmapTransform(new
          CropCircleTransformation(MyApp.getContext())).into(imageView);
    }
  }
}

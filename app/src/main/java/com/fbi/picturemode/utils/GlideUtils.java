package com.fbi.picturemode.utils;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fbi.picturemode.MyApp;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.ContentValues.TAG;

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
      Glide.with(MyApp.getContext()).load(Uri.parse(pictureUrl)).listener(new RequestListener<Uri, GlideDrawable>() {

        @Override
        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean
            isFirstResource) {
          return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable>
            target, boolean isFromMemoryCache, boolean isFirstResource) {
          Log.d(TAG, "onResourceReady: "+isFirstResource+" "+isFromMemoryCache);
          return false;
        }
      }).into(imageView);
    }
  }

  public static void showUserIcon(ImageView imageView, String url) {
    if (imageView != null) {
      Glide.with(MyApp.getContext()).load(Uri.parse(url)).bitmapTransform(new
          CropCircleTransformation(MyApp.getContext())).into(imageView);
    }
  }

  public static void showUserIcon(ImageView imageView, String url,int defaultRes) {
    if (imageView != null) {
      Glide.with(MyApp.getContext()).load(Uri.parse(url)).error(defaultRes).bitmapTransform(new
          CropCircleTransformation(MyApp.getContext())).into(imageView);
    }
  }
}

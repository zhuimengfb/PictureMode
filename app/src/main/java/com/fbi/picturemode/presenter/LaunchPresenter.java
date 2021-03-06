package com.fbi.picturemode.presenter;

import android.net.Uri;
import android.text.TextUtils;

import com.bumptech.glide.request.target.Target;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.activity.views.LaunchView;
import com.fbi.picturemode.entity.UnsplashLocation;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.UnsplashModel;
import com.fbi.picturemode.utils.Constants;
import com.fbi.picturemode.utils.FileUtils;
import com.fbi.picturemode.utils.sharedpreference.UnsplashSharedPreferences;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.Glide.with;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class LaunchPresenter extends BasePresenter<LaunchView> {

  private UnsplashModel unsplashModel;
  private String lastPictureLink = "";
  private String lastPictureId = "";

  public LaunchPresenter(LaunchView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    unsplashModel = new UnsplashModel();
  }

  @Override
  protected void destroyModel() {
    unsplashModel = null;
  }

  public void getRandomPicture() {
    getSubscriptions().add(unsplashModel.getRandomPicture(new Subscriber<UnsplashPicture>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
        showLastPicture();
        if (getView() != null) {
          getView().hideLoading();
        }
      }

      @Override
      public void onNext(final UnsplashPicture unsplashPicture) {
        if (getView() != null) {
          final String id = unsplashPicture.getId();
          final String pictureUrl = unsplashPicture.getUnsplashPictureLinks().getFull();
          final String color = unsplashPicture.getColor();
          final String userIconUrl = unsplashPicture.getUnsplashUser().getUserProfileImage()
              .getLarge();
          final String userName = unsplashPicture.getUnsplashUser().getUserName();
          final UnsplashLocation unsplashLocation = unsplashPicture.getLocation();
          getSubscriptions().add(Observable.just(pictureUrl)
              .map(new Func1<String, File>() {
                @Override
                public File call(String s) {
                  String path = Constants.BASE_PHOTO_TEMP_PATH + "/" + unsplashPicture.getId() +
                      ".png";
                  File file = null;
                  try {
                    file = new File(path);
                    if (!file.exists()) {
                      file.createNewFile();
                    }
                    File originFile = with(MyApp.getContext()).load(Uri.parse(s)).downloadOnly
                        (Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    FileUtils.copyFileUsingFileChannels(originFile, file);
                    File oldFile = new File(UnsplashSharedPreferences.getInstance(MyApp
                        .getContext()).getLastLocalPath());
                    if (oldFile.exists()) {
                      oldFile.delete();
                    }
                    originFile.delete();
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  return file;
                }
              })
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Subscriber<File>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(File file) {
                  if (file != null && file.length() > 1000) {
                    UnsplashSharedPreferences unsplashSharedPreferences = UnsplashSharedPreferences
                        .getInstance(MyApp.getContext());
                    unsplashSharedPreferences.updateLastLocalPath(file.getPath());
                    unsplashSharedPreferences.updateLastRandomPictureLink(pictureUrl);
                    unsplashSharedPreferences.updateLastRandomUserLink(userIconUrl);
                    unsplashSharedPreferences.updateLastRandomUserName(userName);
                    unsplashSharedPreferences.updateLastRandomColor(color);
                    unsplashSharedPreferences.updateLastRandomPictureLinkRegular(unsplashPicture
                        .getUnsplashPictureLinks().getRegular());
                    unsplashSharedPreferences.updateLastPictureId(id);
                    String location = "";
                    if (unsplashLocation != null) {
                      location = unsplashLocation.getCountry() + " " + unsplashLocation.getCity();
                      unsplashSharedPreferences.updateLastRandomLocation
                          (location);
                    } else {
                      unsplashSharedPreferences.updateLastRandomLocation("");
                    }
                  }
                }
              }));
        }
      }
    }));
  }


  public void showLastPicture() {
    if (getView() != null) {
      UnsplashSharedPreferences preferences = UnsplashSharedPreferences.getInstance(MyApp
          .getContext());
      lastPictureLink = preferences.getLastRandomPictureLink();
      String lastLocalPath = preferences.getLastLocalPath();
      lastPictureId = preferences.getLastPictureId();
      if (UserSharedPreferences.getInstance(MyApp.getContext()).isFirstLaunch()) {
        getView().showDefaultPicture();
        getView().hideSetWallpaper();
        UserSharedPreferences.getInstance(MyApp.getContext()).setFirstLauncFalse();
      } else if (TextUtils.isEmpty(lastPictureLink) && TextUtils.isEmpty(lastLocalPath)) {
        getView().showDefaultPicture();
        getView().hideSetWallpaper();
      } else {
        if (TextUtils.isEmpty(lastPictureId)) {
          getView().hideSetWallpaper();
        }
        getView().showLocalPicture(preferences.getLastLocalPath(), preferences
            .getLastRandomPictureLink(), preferences.getLastRandomColor());
        UserSharedPreferences.getInstance(MyApp.getContext()).updateUserIconUrl
            (preferences.getKeyLastRandomPictureLinkRegular());
        getView().showUserIcon(preferences.getLastRandomUserLink());
        getView().showLocation(preferences.getLastRandomLocation());
        getView().showUserName(preferences.getLastRandomUserName());
      }
    }
  }

  public void setWallpaper() {
    getView().showLoading();
    getSubscriptions().add(unsplashModel.downloadPicture(lastPictureLink,
        lastPictureId, new Subscriber<String>() {

          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onNext(String code) {
            getView().hideLoading();
            if (Constants.CODE_PICTURE_DOWNLOAD_FAIL.equals(code)) {
              getView().showSetWallpaperFail();
            } else if (Constants.CODE_PICTURE_DOWNLOAD_ALREADY.equals(code)) {
            } else {
              getView().setWallpaper(code);
            }
          }
        }));
  }
}

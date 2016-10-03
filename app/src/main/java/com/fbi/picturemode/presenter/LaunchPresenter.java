package com.fbi.picturemode.presenter;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.activity.views.LaunchView;
import com.fbi.picturemode.entity.UnsplashLocation;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.model.UnsplashModel;
import com.fbi.picturemode.utils.sharedpreference.UnsplashSharedPreferences;

import rx.Subscriber;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class LaunchPresenter extends BasePresenter<LaunchView> {

  private UnsplashModel unsplashModel;

  public LaunchPresenter(LaunchView baseView) {
    super(baseView);
  }

  @Override
  protected void initModel() {
    unsplashModel = new UnsplashModel();
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
        getView().hideLoading();
      }

      @Override
      public void onNext(UnsplashPicture unsplashPicture) {
        if (getView() != null) {
          String pictureUrl = unsplashPicture.getUnsplashPictureLinks().getRegular();
          String color = unsplashPicture.getColor();
          String userIconUrl = unsplashPicture.getUnsplashUser().getUserProfileImage().getMedium();
          String userName = unsplashPicture.getUnsplashUser().getUserName();
          UnsplashSharedPreferences unsplashSharedPreferences = UnsplashSharedPreferences
              .getInstance(MyApp.getContext());
          unsplashSharedPreferences.updateLastRandomPictureLink(pictureUrl);
          unsplashSharedPreferences.updateLastRandomUserLink(userIconUrl);
          unsplashSharedPreferences.updateLastRandomUserName(userName);
          unsplashSharedPreferences.updateLastRandomColor(color);
          UnsplashLocation unsplashLocation = unsplashPicture.getLocation();
          String location = "";
          if (unsplashLocation != null) {
            location = unsplashLocation.getCountry() + " " + unsplashLocation.getCity();
            unsplashSharedPreferences.updateLastRandomLocation
                (location);
          } else {
            unsplashSharedPreferences.updateLastRandomLocation("");
          }
          getView().showPicture(pictureUrl, color);
          getView().showUserIcon(userIconUrl);
          getView().showLocation(location);
          getView().showUserName(userName);
          getView().hideLoading();
        }
      }
    }));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unsplashModel = null;
  }

  public void showLastPicture() {
    UnsplashSharedPreferences preferences = UnsplashSharedPreferences.getInstance(MyApp
        .getContext());
    getView().showPicture(preferences.getLastRandomPictureLink(), preferences
        .getLastRandomColor());
    getView().showUserIcon(preferences.getLastRandomUserLink());
    getView().showLocation(preferences.getLastRandomLocation());
    getView().showUserName(preferences.getLastRandomUserName());
  }
}

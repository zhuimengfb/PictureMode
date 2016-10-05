package com.fbi.picturemode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.picturemode.db.contract.UserContract;
import com.fbi.picturemode.db.sqlitehelper.UnsplashUserSQLiteHelper;
import com.fbi.picturemode.entity.UnsplashUser;
import com.fbi.picturemode.entity.UnsplashUserProfileLinks;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UnsplashUserDataHelper {

  private static final String DB_NAME = UnsplashUserSQLiteHelper.TB_NAME+".db";
  private static final int DB_VERSION = 1;
  private static SQLiteDatabase mSQLiteDatabase;
  private static UnsplashUserSQLiteHelper unsplashUserSQLiteHelper;
  private static UnsplashUserDataHelper dataHelper;

  private UnsplashUserDataHelper(Context context) {
    unsplashUserSQLiteHelper = new UnsplashUserSQLiteHelper(context, DB_NAME, null,
        DB_VERSION);
    mSQLiteDatabase = unsplashUserSQLiteHelper.getWritableDatabase();
  }

  public static UnsplashUserDataHelper getInstance(Context context) {
    if (dataHelper == null) {
      synchronized (UnsplashUserDataHelper.class) {
        if (dataHelper == null) {
          dataHelper = new UnsplashUserDataHelper(context);
        }
      }
    }
    return dataHelper;
  }


  public void insertUser(UnsplashUser user) {
    if (!hasUser(user.getId())) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(UserContract.BIO, user.getBio());
      contentValues.put(UserContract.ID, user.getId());
      contentValues.put(UserContract.LOCATION, user.getLocation());
      contentValues.put(UserContract.NAME, user.getName());
      contentValues.put(UserContract.PORTFOLIO_URL, user.getPortfolioUrl());
      contentValues.put(UserContract.PROFILE_IMAGE_LARGE, user.getUserProfileImage().getLarge());
      contentValues.put(UserContract.TOTAL_COLLECTIONS, user.getTotalCollections());
      contentValues.put(UserContract.TOTAL_LIKES, user.getTotalLikes());
      contentValues.put(UserContract.TOTAL_PHOTOS, user.getTotalPhotos());
      contentValues.put(UserContract.USER_NAME, user.getUserName());
      mSQLiteDatabase.insert(UnsplashUserSQLiteHelper.TB_NAME, null, contentValues);
    }
  }


  public boolean hasUser(String id) {
    return queryUserById(id) != null;
  }

  public UnsplashUser queryUserById(String id) {
    UnsplashUser user = null;
    Cursor cusor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashUserSQLiteHelper
        .TB_NAME + " WHERE " + UserContract.ID + " =?", new String[]{id});
    while (cusor.moveToNext()) {
      user = getUserFromCursor(cusor);
    }
    return user;
  }

  public void updateUser(UnsplashUser user) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(UserContract.BIO, user.getBio());
    contentValues.put(UserContract.ID, user.getId());
    contentValues.put(UserContract.LOCATION, user.getLocation());
    contentValues.put(UserContract.NAME, user.getName());
    contentValues.put(UserContract.PORTFOLIO_URL, user.getPortfolioUrl());
    contentValues.put(UserContract.PROFILE_IMAGE_LARGE, user.getUserProfileImage().getLarge());
    contentValues.put(UserContract.TOTAL_COLLECTIONS, user.getTotalCollections());
    contentValues.put(UserContract.TOTAL_LIKES, user.getTotalLikes());
    contentValues.put(UserContract.TOTAL_PHOTOS, user.getTotalPhotos());
    contentValues.put(UserContract.USER_NAME, user.getUserName());
    mSQLiteDatabase.update(UnsplashUserSQLiteHelper.TB_NAME, contentValues, UserContract.ID
        + "=?", new String[]{user.getId()});
  }

  private UnsplashUser getUserFromCursor(Cursor cursor) {
    UnsplashUser user = new UnsplashUser();
    if (cursor != null) {
      user.setId(cursor.getString(cursor.getColumnIndex(UserContract.ID)));
      user.setBio(cursor.getString(cursor.getColumnIndex(UserContract.BIO)));
      user.setLocation(cursor.getString(cursor.getColumnIndex(UserContract.LOCATION)));
      user.setName(cursor.getString(cursor.getColumnIndex(UserContract.NAME)));
      user.setPortfolioUrl(cursor.getString(cursor.getColumnIndex(UserContract.PORTFOLIO_URL)));
      user.setTotalCollections(cursor.getInt(cursor.getColumnIndex(UserContract
          .TOTAL_COLLECTIONS)));
      user.setTotalLikes(cursor.getInt(cursor.getColumnIndex(UserContract.TOTAL_LIKES)));
      user.setTotalPhotos(cursor.getInt(cursor.getColumnIndex(UserContract.TOTAL_PHOTOS)));
      user.setUserName(cursor.getString(cursor.getColumnIndex(UserContract.USER_NAME)));
      UnsplashUserProfileLinks links = new UnsplashUserProfileLinks();
      links.setLarge(cursor.getString(cursor.getColumnIndex(UserContract.PROFILE_IMAGE_LARGE)));
      user.setUserProfileImage(links);
    }
    return user;
  }

  public UnsplashUser queryUserByUserName(String userName) {
    UnsplashUser user = null;
    Cursor cusor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashUserSQLiteHelper
        .TB_NAME + " WHERE " + UserContract.USER_NAME + " =?", new String[]{userName});
    while (cusor.moveToNext()) {
      user = getUserFromCursor(cusor);
    }
    return user;
  }
}

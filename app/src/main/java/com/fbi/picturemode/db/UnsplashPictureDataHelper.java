package com.fbi.picturemode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.db.contract.PictureContract;
import com.fbi.picturemode.db.sqlitehelper.UnsplashPictureSQLiteHelper;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.entity.UnsplashPictureLinks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class UnsplashPictureDataHelper {

  private static final String DB_NAME = UnsplashPictureSQLiteHelper.TB_NAME + ".db";
  private static final int DB_VERSION = 1;
  private static SQLiteDatabase mSQLiteDatabase;
  private static UnsplashPictureSQLiteHelper unsplashPictureSQLiteHelper;
  private static UnsplashPictureDataHelper dataHelper;

  private UnsplashPictureDataHelper(Context context) {
    unsplashPictureSQLiteHelper = new UnsplashPictureSQLiteHelper(context, DB_NAME, null,
        DB_VERSION);
    mSQLiteDatabase = unsplashPictureSQLiteHelper.getWritableDatabase();
  }

  public static UnsplashPictureDataHelper getInstance(Context context) {
    if (dataHelper == null) {
      synchronized (UnsplashPictureDataHelper.class) {
        if (dataHelper == null) {
          dataHelper = new UnsplashPictureDataHelper(context);
        }
      }
    }
    return dataHelper;
  }

  public void insertPicture(UnsplashPicture picture) {
    if (!hasPicture(picture.getId())) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(PictureContract.COLOR, picture.getColor());
      contentValues.put(PictureContract.DOWNLOADS, picture.getDownloads());
      if (picture.getExifInfo() != null) {
        contentValues.put(PictureContract.EXIF_ISO, picture.getExifInfo().getIso());
        contentValues.put(PictureContract.EXIF_MODEL, picture.getExifInfo().getModel());
      }
      contentValues.put(PictureContract.HEIGHT, picture.getHeight());
      contentValues.put(PictureContract.ID, picture.getId());
      contentValues.put(PictureContract.LIKES, picture.getLikes());
      contentValues.put(PictureContract.LINK_RAW, picture.getUnsplashPictureLinks().getRaw());
      contentValues.put(PictureContract.LINK_FULL, picture.getUnsplashPictureLinks().getFull());
      contentValues.put(PictureContract.LINK_REGULAR, picture.getUnsplashPictureLinks()
          .getRegular());
      contentValues.put(PictureContract.USER_ID, picture.getUnsplashUser().getId());
      contentValues.put(PictureContract.WIDTH, picture.getWidth());
      contentValues.put(PictureContract.CREATED_TIME, picture.getCreatedTime().getTime());
      mSQLiteDatabase.insert(UnsplashPictureSQLiteHelper.TB_NAME, null, contentValues);
    }
  }


  public boolean hasPicture(String id) {
    return queryPictureById(id) != null;
  }

  public UnsplashPicture queryPictureById(String id) {
    UnsplashPicture picture = null;
    Cursor cusor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashPictureSQLiteHelper
        .TB_NAME + " WHERE " + PictureContract.ID + " =?", new String[]{id});
    while (cusor.moveToNext()) {
      picture = getPictureFromCursor(cusor);
    }
    return picture;
  }

  public List<UnsplashPicture> queryPagePictures(int page, int pageNum) {
    List<UnsplashPicture> pictures = new ArrayList<>();
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashPictureSQLiteHelper
        .TB_NAME + " ORDER BY " + PictureContract.CREATED_TIME + " desc LIMIT ?  OFFSET ? ", new
        String[]{"" + pageNum, "" + (page - 1) * pageNum});
    while (cursor.moveToNext()) {
      pictures.add(getPictureFromCursor(cursor));
    }
    return pictures;
  }

  public void updatePicture(UnsplashPicture picture) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(PictureContract.COLOR, picture.getColor());
    contentValues.put(PictureContract.DOWNLOADS, picture.getDownloads());
    if (picture.getExifInfo() != null) {
      contentValues.put(PictureContract.EXIF_ISO, picture.getExifInfo().getIso());
      contentValues.put(PictureContract.EXIF_MODEL, picture.getExifInfo().getModel());
    }
    contentValues.put(PictureContract.HEIGHT, picture.getHeight());
    contentValues.put(PictureContract.ID, picture.getId());
    contentValues.put(PictureContract.LIKES, picture.getLikes());
    contentValues.put(PictureContract.LINK_RAW, picture.getUnsplashPictureLinks().getRaw());
    contentValues.put(PictureContract.LINK_FULL, picture.getUnsplashPictureLinks().getFull());
    contentValues.put(PictureContract.LINK_REGULAR, picture.getUnsplashPictureLinks()
        .getRegular());
    contentValues.put(PictureContract.USER_ID, picture.getUnsplashUser().getId());
    contentValues.put(PictureContract.WIDTH, picture.getWidth());
    contentValues.put(PictureContract.CREATED_TIME, picture.getCreatedTime().getTime());
    mSQLiteDatabase.update(UnsplashPictureSQLiteHelper.TB_NAME, contentValues, PictureContract.ID
        + "=?", new String[]{picture.getId()});
  }

  private UnsplashPicture getPictureFromCursor(Cursor cursor) {
    UnsplashPicture picture = new UnsplashPicture();
    if (cursor != null) {
      picture.setId(cursor.getString(cursor.getColumnIndex(PictureContract.ID)));
      picture.setColor(cursor.getString(cursor.getColumnIndex(PictureContract.COLOR)));
      picture.setDownloads(cursor.getInt(cursor.getColumnIndex(PictureContract.DOWNLOADS)));
      picture.setHeight(cursor.getInt(cursor.getColumnIndex(PictureContract.HEIGHT)));
      picture.setLikes(cursor.getInt(cursor.getColumnIndex(PictureContract.LIKES)));
      picture.setWidth(cursor.getInt(cursor.getColumnIndex(PictureContract.WIDTH)));
      picture.setUnsplashUser(UnsplashUserDataHelper.getInstance(MyApp.getContext())
          .queryUserById(cursor.getString(cursor.getColumnIndex(PictureContract.USER_ID))));
      UnsplashPictureLinks links = new UnsplashPictureLinks();
      links.setFull(cursor.getString(cursor.getColumnIndex(PictureContract.LINK_FULL)));
      links.setRegular(cursor.getString(cursor.getColumnIndex(PictureContract.LINK_REGULAR)));
      links.setRaw(cursor.getString(cursor.getColumnIndex(PictureContract.LINK_RAW)));
      picture.setUnsplashPictureLinks(links);
      picture.setCreatedTime(new Date(cursor.getLong(cursor.getColumnIndex(PictureContract
          .CREATED_TIME))));
    }
    return picture;
  }

  public List<UnsplashPicture> queryUserPagePictures(String userId, int page, int pageNum) {
    List<UnsplashPicture> pictures = new ArrayList<>();
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashPictureSQLiteHelper
        .TB_NAME + " WHERE " + PictureContract.USER_ID + " =? ORDER BY " + PictureContract
        .CREATED_TIME + " desc LIMIT ?  OFFSET ? ", new
        String[]{userId, "" + pageNum, "" + (page - 1) * pageNum});
    while (cursor.moveToNext()) {
      pictures.add(getPictureFromCursor(cursor));
    }
    return pictures;
  }
}

package com.fbi.picturemode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.picturemode.db.contract.MyDownloadContract;
import com.fbi.picturemode.db.sqlitehelper.MyDownloadSQLiteHelper;
import com.fbi.picturemode.entity.MyDownload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class MyDownloadDataHelper {

  private static final String DB_NAME = MyDownloadSQLiteHelper.TB_NAME + ".db";
  private static final int DB_VERSION = 1;
  private static SQLiteDatabase mSQLiteDatabase;
  private static MyDownloadSQLiteHelper myDownloadSQLiteHelper;
  private static MyDownloadDataHelper dataHelper;

  private MyDownloadDataHelper(Context context) {
    myDownloadSQLiteHelper = new MyDownloadSQLiteHelper(context, DB_NAME, null,
        DB_VERSION);
    mSQLiteDatabase = myDownloadSQLiteHelper.getWritableDatabase();
  }

  public static MyDownloadDataHelper getInstance(Context context) {
    if (dataHelper == null) {
      synchronized (MyDownloadDataHelper.class) {
        if (dataHelper == null) {
          dataHelper = new MyDownloadDataHelper(context);
        }
      }
    }
    return dataHelper;
  }

  public void insertMyDownload(String photoId,String localAddress) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MyDownloadContract.PHOTO_ID, photoId);
    contentValues.put(MyDownloadContract.LOACAL_ADDRESS, localAddress);
    contentValues.put(MyDownloadContract.CREATED_TIME,new Date().getTime());
    mSQLiteDatabase.insert(MyDownloadSQLiteHelper.TB_NAME, null, contentValues);
  }

  public boolean downloadAlready(String photoId) {
    return queryMyDownloadByPhotoId(photoId) != null;
  }

  public List<MyDownload> queryMyDownload() {
    List<MyDownload> myDownloads = new ArrayList<>();
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + MyDownloadSQLiteHelper.TB_NAME +
        " order by " + MyDownloadContract.CREATED_TIME + " desc", new String[]{});
    while (cursor.moveToNext()) {
      myDownloads.add(getMyDownloadFromCursor(cursor));
    }
    return myDownloads;
  }

  public void deleteMyDownload(String photoId) {
    mSQLiteDatabase.delete(MyDownloadSQLiteHelper.TB_NAME, MyDownloadContract
        .PHOTO_ID + " = ?", new String[]{photoId});
  }

  public MyDownload queryMyDownloadByPhotoId(String photoId) {
    MyDownload myDownload = null;
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + MyDownloadSQLiteHelper.TB_NAME +
        " WHERE " + MyDownloadContract.PHOTO_ID + " =?", new String[]{photoId});
    while (cursor.moveToNext()){
      myDownload = getMyDownloadFromCursor(cursor);
    }
    return myDownload;
  }

  private MyDownload getMyDownloadFromCursor(Cursor cursor) {
    MyDownload myDownload = new MyDownload();
    if (cursor != null) {
      myDownload.setId(cursor.getInt(cursor.getColumnIndex(MyDownloadContract.ID)));
      myDownload.setCreatedTime(cursor.getLong(cursor.getColumnIndex(MyDownloadContract
          .CREATED_TIME)));
      myDownload.setLocalAddress(cursor.getString(cursor.getColumnIndex(MyDownloadContract
          .LOACAL_ADDRESS)));
      myDownload.setPhotoId(cursor.getString(cursor.getColumnIndex(MyDownloadContract.PHOTO_ID)));
    }
    return myDownload;
  }
}

package com.fbi.picturemode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.picturemode.db.contract.MyCollectContract;
import com.fbi.picturemode.db.sqlitehelper.MyCollectSQLiteHelper;
import com.fbi.picturemode.entity.MyCollect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollectDataHelper {

  private static final String DB_NAME = MyCollectSQLiteHelper.TB_NAME + ".db";
  private static final int DB_VERSION = 1;
  private static SQLiteDatabase mSQLiteDatabase;
  private static MyCollectSQLiteHelper myCollectSQLiteHelper;
  private static MyCollectDataHelper dataHelper;

  private MyCollectDataHelper(Context context) {
    myCollectSQLiteHelper = new MyCollectSQLiteHelper(context, DB_NAME, null,
        DB_VERSION);
    mSQLiteDatabase = myCollectSQLiteHelper.getWritableDatabase();
  }

  public static MyCollectDataHelper getInstance(Context context) {
    if (dataHelper == null) {
      synchronized (MyCollectDataHelper.class) {
        if (dataHelper == null) {
          dataHelper = new MyCollectDataHelper(context);
        }
      }
    }
    return dataHelper;
  }

  public void insertMyCollect(MyCollect myCollect) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MyCollectContract.COLLECT_ID, myCollect.getCollectId());
    contentValues.put(MyCollectContract.COLLECT_TIME, new Date().getTime());
    contentValues.put(MyCollectContract.FLAG, MyCollectContract.FLAG_NORMAL);
    contentValues.put(MyCollectContract.TYPE, myCollect.getType());
    mSQLiteDatabase.insert(MyCollectSQLiteHelper.TB_NAME, null, contentValues);
  }

  public void insertMyCollect(String collectId, int type) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MyCollectContract.COLLECT_ID, collectId);
    contentValues.put(MyCollectContract.COLLECT_TIME, new Date().getTime());
    contentValues.put(MyCollectContract.FLAG, MyCollectContract.FLAG_NORMAL);
    contentValues.put(MyCollectContract.TYPE, type);
    mSQLiteDatabase.insert(MyCollectSQLiteHelper.TB_NAME, null, contentValues);
  }

  public List<MyCollect> queryMyCollectByType(int type) {
    List<MyCollect> myCollects = new ArrayList<>();
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + MyCollectSQLiteHelper.TB_NAME + "" +
        " WHERE " + MyCollectContract.TYPE + " = ? AND " + MyCollectContract.FLAG + " =? ", new
        String[]{"" + type, "" + MyCollectContract.FLAG_NORMAL});
    while (cursor.moveToNext()) {
      myCollects.add(getMyCollectFromCursor(cursor));
    }
    return myCollects;
  }

  public boolean hasCollectAlready(String collectId) {
    return queryCollectByCollectId(collectId) != null;
  }

  public MyCollect queryCollectByCollectId(String collectId) {
    MyCollect myCollect = null;
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + MyCollectSQLiteHelper.TB_NAME + "" +
            " WHERE " + MyCollectContract.COLLECT_ID + " =? AND " + MyCollectContract.FLAG + " =?",
        new String[]{collectId, "" + MyCollectContract.FLAG_NORMAL});
    while (cursor.moveToNext()) {
      myCollect = getMyCollectFromCursor(cursor);
    }
    return myCollect;
  }

  public void deleteMyCollect(String collectId) {
    mSQLiteDatabase.delete(MyCollectSQLiteHelper.TB_NAME, MyCollectContract.COLLECT_ID + " = ? ",
        new String[]{collectId});
  }

  public MyCollect getMyCollectFromCursor(Cursor cursor) {
    MyCollect myCollect = new MyCollect();
    if (cursor != null) {
      myCollect.setId(cursor.getInt(cursor.getColumnIndex(MyCollectContract.ID)));
      myCollect.setCollectId(cursor.getString(cursor.getColumnIndex(MyCollectContract.COLLECT_ID)));
      myCollect.setCollectTime(cursor.getLong(cursor.getColumnIndex(MyCollectContract
          .COLLECT_TIME)));
      myCollect.setFlag(cursor.getInt(cursor.getColumnIndex(MyCollectContract.FLAG)));
      myCollect.setType(cursor.getInt(cursor.getColumnIndex(MyCollectContract.TYPE)));
    }
    return myCollect;
  }
}

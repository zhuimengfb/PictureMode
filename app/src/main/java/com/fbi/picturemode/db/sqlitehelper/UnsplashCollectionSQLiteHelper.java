package com.fbi.picturemode.db.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.picturemode.db.contract.CollectionContract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UnsplashCollectionSQLiteHelper extends SQLiteOpenHelper {
  public static final String TB_NAME = "tb_unsplash_collection";

  public UnsplashCollectionSQLiteHelper(Context context, String name, SQLiteDatabase
      .CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + CollectionContract.ID +
        " primary key, " + CollectionContract.COVER_ID + " varchar, " + CollectionContract
        .DESCRIPTION + " varchar, " + CollectionContract.PHOTO_IDS + " varchar, " +
        CollectionContract.PUBLISH_TIME + " integer, " + CollectionContract
        .RELATED_COLLECTION_IDS + " varchar, " + CollectionContract.TITLE + " varchar, " +
        CollectionContract.TOTAL_PHOTOS + " integer, " + CollectionContract.USER_ID + " varchar)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    onCreate(sqLiteDatabase);
  }
}

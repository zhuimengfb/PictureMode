package com.fbi.picturemode.db.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.picturemode.db.contract.PictureContract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class UnsplashPictureSQLiteHelper extends SQLiteOpenHelper {

  public static final String TB_NAME = "tb_unsplash_picture";


  public UnsplashPictureSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory
      factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + PictureContract.ID +
        " primary key, " + PictureContract.COLOR + " varchar, " + PictureContract.DOWNLOADS +
        " integer, " + PictureContract.EXIF_ISO + " integer, " + PictureContract.EXIF_MODEL +
        " varchar, " + PictureContract.HEIGHT + " integer, " + PictureContract.LIKES +
        " integer, " + PictureContract.LINK_RAW + " varchar, " + PictureContract.LINK_FULL +
        " varchar, " + PictureContract.LINK_REGULAR + " varchar, " + PictureContract.USER_ID +
        " varchar, " + PictureContract.WIDTH + " integer, "+PictureContract.CREATED_TIME+" integer)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    onCreate(sqLiteDatabase);
  }
}

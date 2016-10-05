package com.fbi.picturemode.db.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.picturemode.db.contract.UserContract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UnsplashUserSQLiteHelper extends SQLiteOpenHelper {

  public static final String TB_NAME = "tb_unsplash_user";

  public UnsplashUserSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory
      factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + UserContract.ID +
        " primary key, " + UserContract.BIO + " varchar, " + UserContract.LOCATION +
        " varchar, " + UserContract.NAME + " varchar, " + UserContract.PORTFOLIO_URL +
        " varchar, " + UserContract.PROFILE_IMAGE_LARGE + " varchar, " + UserContract
        .TOTAL_COLLECTIONS + " integer, " + UserContract.TOTAL_LIKES + " integer, " +
        UserContract.TOTAL_PHOTOS + " integer, " + UserContract.USER_NAME + " varchar )");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    onCreate(sqLiteDatabase);
  }
}

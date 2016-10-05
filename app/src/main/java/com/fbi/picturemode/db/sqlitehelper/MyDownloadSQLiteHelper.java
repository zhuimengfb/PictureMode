package com.fbi.picturemode.db.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.picturemode.db.contract.MyDownloadContract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class MyDownloadSQLiteHelper extends SQLiteOpenHelper {
  public static final String TB_NAME = "tb_my_download";

  public MyDownloadSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory
      factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + MyDownloadContract.ID +
        " integer primary key AUTOINCREMENT, " + MyDownloadContract.CREATED_TIME + " integer, " +
        MyDownloadContract.LOACAL_ADDRESS + " varchar, " + MyDownloadContract.PHOTO_ID + " " +
        "varchar)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    onCreate(sqLiteDatabase);
  }
}

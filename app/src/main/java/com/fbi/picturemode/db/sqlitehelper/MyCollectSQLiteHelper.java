package com.fbi.picturemode.db.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.picturemode.db.contract.MyCollectContract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollectSQLiteHelper extends SQLiteOpenHelper {
  public static final String TB_NAME = "tb_my_collect";

  public MyCollectSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory
      factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + MyCollectContract.ID +
        " integer primary key AUTOINCREMENT, " + MyCollectContract.COLLECT_ID + " varchar, " +
        MyCollectContract.COLLECT_TIME + " integer, " + MyCollectContract.FLAG + " integer, " +
        MyCollectContract.TYPE +
        " integer)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    onCreate(sqLiteDatabase);
  }
}

package com.fbi.picturemode.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.db.contract.CollectionContract;
import com.fbi.picturemode.db.sqlitehelper.UnsplashCollectionSQLiteHelper;
import com.fbi.picturemode.entity.UnsplashCollection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UnsplashCollectionDataHelper {

  private static final String DB_NAME = UnsplashCollectionSQLiteHelper.TB_NAME + ".db";
  private static final int DB_VERSION = 1;
  private static SQLiteDatabase mSQLiteDatabase;
  private static UnsplashCollectionSQLiteHelper unsplashCollectionSQLiteHelper;
  private static UnsplashCollectionDataHelper dataHelper;

  private UnsplashCollectionDataHelper(Context context) {
    unsplashCollectionSQLiteHelper = new UnsplashCollectionSQLiteHelper(context, DB_NAME, null,
        DB_VERSION);
    mSQLiteDatabase = unsplashCollectionSQLiteHelper.getWritableDatabase();
  }

  public static UnsplashCollectionDataHelper getInstance(Context context) {
    if (dataHelper == null) {
      synchronized (UnsplashCollectionDataHelper.class) {
        if (dataHelper == null) {
          dataHelper = new UnsplashCollectionDataHelper(context);
        }
      }
    }
    return dataHelper;
  }

  public void insertCollection(UnsplashCollection collection) {
    if (!hasCollection("" + collection.getId())) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(CollectionContract.COVER_ID, collection.getCover().getId());
      contentValues.put(CollectionContract.DESCRIPTION, collection.getDescription());
      contentValues.put(CollectionContract.ID, collection.getId() + "");
      contentValues.put(CollectionContract.PHOTO_IDS, collection.getPhotos());
      contentValues.put(CollectionContract.RELATED_COLLECTION_IDS, collection.getRelatedCollections
          ());
      contentValues.put(CollectionContract.PUBLISH_TIME, collection.getPublishTime().getTime());
      contentValues.put(CollectionContract.TITLE, collection.getTitle());
      contentValues.put(CollectionContract.TOTAL_PHOTOS, collection.getTotalPhotos());
      contentValues.put(CollectionContract.USER_ID, collection.getUser().getId());
      mSQLiteDatabase.insert(UnsplashCollectionSQLiteHelper.TB_NAME, null, contentValues);
    }
  }


  public boolean hasCollection(String id) {
    return queryCollectionById(id) != null;
  }

  public UnsplashCollection queryCollectionById(String id) {
    UnsplashCollection collection = null;
    Cursor cusor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashCollectionSQLiteHelper
        .TB_NAME + " WHERE " + CollectionContract.ID + " =?", new String[]{id});
    while (cusor.moveToNext()) {
      collection = getCollectionFromCursor(cusor);
    }
    return collection;
  }

  public List<UnsplashCollection> queryPageCollections(int page, int pageNum) {
    List<UnsplashCollection> collections = new ArrayList<>();
    Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + UnsplashCollectionSQLiteHelper
        .TB_NAME + " ORDER BY " + CollectionContract.PUBLISH_TIME + " LIMIT ?  OFFSET ? ", new
        String[]{"" + pageNum, "" + (page - 1) * pageNum});
    while (cursor.moveToNext()) {
      collections.add(getCollectionFromCursor(cursor));
    }
    return collections;
  }

  public void updateCollection(UnsplashCollection collection) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(CollectionContract.COVER_ID, collection.getCover().getId());
    contentValues.put(CollectionContract.DESCRIPTION, collection.getDescription());
    contentValues.put(CollectionContract.ID, collection.getId());
    contentValues.put(CollectionContract.PHOTO_IDS, collection.getPhotos());
    contentValues.put(CollectionContract.RELATED_COLLECTION_IDS, collection.getRelatedCollections
        ());
    contentValues.put(CollectionContract.PUBLISH_TIME, collection.getPublishTime().getTime());
    contentValues.put(CollectionContract.TITLE, collection.getTitle());
    contentValues.put(CollectionContract.TOTAL_PHOTOS, collection.getTotalPhotos());
    contentValues.put(CollectionContract.USER_ID, collection.getUser().getId());
    mSQLiteDatabase.update(UnsplashCollectionSQLiteHelper.TB_NAME, contentValues,
        CollectionContract.ID + "=?", new String[]{"" + collection.getId()});
  }

  private UnsplashCollection getCollectionFromCursor(Cursor cursor) {
    UnsplashCollection collection = new UnsplashCollection();
    if (cursor != null) {
      collection.setTotalPhotos(cursor.getInt(cursor.getColumnIndex(CollectionContract
          .TOTAL_PHOTOS)));
      collection.setCover(UnsplashPictureDataHelper.getInstance(MyApp.getContext())
          .queryPictureById(cursor.getString(cursor.getColumnIndex(CollectionContract.COVER_ID))));
      collection.setDescription(cursor.getString(cursor.getColumnIndex(CollectionContract
          .DESCRIPTION)));
      collection.setId(cursor.getInt(cursor.getColumnIndex(CollectionContract.ID)));
      collection.setPublishTime(new Date(cursor.getLong(cursor.getColumnIndex(CollectionContract
          .PUBLISH_TIME))));
      collection.setTitle(cursor.getString(cursor.getColumnIndex(CollectionContract.TITLE)));
      collection.setUser(UnsplashUserDataHelper.getInstance(MyApp.getContext()).queryUserById
          (cursor.getString(cursor.getColumnIndex(CollectionContract.USER_ID))));
      collection.setPhotos(cursor.getString(cursor.getColumnIndex(CollectionContract.PHOTO_IDS)));
      collection.setRelatedCollections(cursor.getString(cursor.getColumnIndex(CollectionContract
          .RELATED_COLLECTION_IDS)));
    }
    return collection;
  }


  public void updateCollectionPhotos(int collectionId, String photos) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(CollectionContract.PHOTO_IDS, photos);
    mSQLiteDatabase.update(UnsplashCollectionSQLiteHelper.TB_NAME, contentValues,
        CollectionContract.ID + "=?", new String[]{"" + collectionId});
  }

  public void updateCollectionRelatedCollections(int collectionId, String collectionIds) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(CollectionContract.RELATED_COLLECTION_IDS, collectionIds);
    mSQLiteDatabase.update(UnsplashCollectionSQLiteHelper.TB_NAME, contentValues,
        CollectionContract.ID + "=?", new String[]{"" + collectionId});
  }
}

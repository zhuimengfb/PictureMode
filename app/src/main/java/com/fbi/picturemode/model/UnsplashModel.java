package com.fbi.picturemode.model;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.db.MyCollectDataHelper;
import com.fbi.picturemode.db.MyDownloadDataHelper;
import com.fbi.picturemode.db.UnsplashCollectionDataHelper;
import com.fbi.picturemode.db.UnsplashPictureDataHelper;
import com.fbi.picturemode.db.UnsplashUserDataHelper;
import com.fbi.picturemode.db.contract.MyCollectContract;
import com.fbi.picturemode.entity.MyCollect;
import com.fbi.picturemode.entity.MyDownload;
import com.fbi.picturemode.entity.UnsplashCollection;
import com.fbi.picturemode.entity.UnsplashPicture;
import com.fbi.picturemode.entity.UnsplashUser;
import com.fbi.picturemode.model.apimanager.UnsplashApiManager;
import com.fbi.picturemode.utils.Constants;
import com.fbi.picturemode.utils.FileUtils;
import com.fbi.picturemode.utils.sharedpreference.UserSharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */

public class UnsplashModel extends BaseModel {


  public Subscription getRandomPicture(Subscriber<UnsplashPicture> subscriber) {
    return UnsplashApiManager.getRandomPicture()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPagePictures(int page, int pageNum, Subscriber<List<UnsplashPicture>>
      subscriber) {
    return UnsplashApiManager.getPagePictures(page, pageNum)
        .doOnNext(new Action1<List<UnsplashPicture>>() {
          @Override
          public void call(List<UnsplashPicture> unsplashPictures) {
            for (UnsplashPicture picture : unsplashPictures) {
              UnsplashPictureDataHelper.getInstance(MyApp.getContext()).insertPicture(picture);
              UnsplashUserDataHelper.getInstance(MyApp.getContext()).insertUser(picture
                  .getUnsplashUser());
            }
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPageCollections(int page, int pageNum,
                                         Subscriber<List<UnsplashCollection>> subscriber) {
    return UnsplashApiManager.getPageCollections(page, pageNum)
        .doOnNext(new Action1<List<UnsplashCollection>>() {
          @Override
          public void call(List<UnsplashCollection> collections) {
            for (UnsplashCollection collection : collections) {
              UnsplashCollectionDataHelper.getInstance(MyApp.getContext()).insertCollection
                  (collection);
              UnsplashUserDataHelper.getInstance(MyApp.getContext()).insertUser(collection
                  .getUser());
              UnsplashPictureDataHelper.getInstance(MyApp.getContext()).insertPicture(collection
                  .getCover());
            }
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getDetailPicture(String id, Subscriber<UnsplashPicture> subscriber) {
    return UnsplashApiManager.getDetailPicture(id)
        .doOnNext(new Action1<UnsplashPicture>() {
          @Override
          public void call(UnsplashPicture unsplashPicture) {
            UnsplashPictureDataHelper.getInstance(MyApp.getContext()).updatePicture
                (unsplashPicture);
            UnsplashUserDataHelper.getInstance(MyApp.getContext()).updateUser(unsplashPicture
                .getUnsplashUser());
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getUserPictures(String userName, int page, int pageNum,
                                      Subscriber<List<UnsplashPicture>> subscriber) {
    return UnsplashApiManager.getUserPictures(userName, page, pageNum)
        .doOnNext(new Action1<List<UnsplashPicture>>() {
          @Override
          public void call(List<UnsplashPicture> unsplashPictures) {
            for (UnsplashPicture picture : unsplashPictures) {
              UnsplashPictureDataHelper.getInstance(MyApp.getContext()).insertPicture(picture);
            }
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getCollectionPictures(final int collectionId, final int page, int pageNum,
                                            Subscriber<List<UnsplashPicture>> subscriber) {
    return UnsplashApiManager.getCollectionPictures(collectionId, page, pageNum)
        .doOnNext(new Action1<List<UnsplashPicture>>() {
          @Override
          public void call(List<UnsplashPicture> unsplashPictures) {
            if (unsplashPictures.size() > 0) {
              String photos = "";
              for (UnsplashPicture picture : unsplashPictures) {
                UnsplashPictureDataHelper.getInstance(MyApp.getContext()).insertPicture(picture);
                UnsplashUserDataHelper.getInstance(MyApp.getContext()).insertUser(picture
                    .getUnsplashUser());
                photos += picture.getId() + ",";
              }
              UnsplashCollectionDataHelper.getInstance(MyApp.getContext()).updateCollectionPhotos
                  (collectionId, photos.substring(0, photos.length() - 1));
            }
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getDetailCollection(int collectionId, Subscriber<UnsplashCollection>
      subscriber) {
    return UnsplashApiManager.getDetailCollection(collectionId)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getRelatedCollections(final int collectionId,
                                            Subscriber<List<UnsplashCollection>>
                                                subscriber) {
    return UnsplashApiManager.getRelatedCollections(collectionId)
        .doOnNext(new Action1<List<UnsplashCollection>>() {
          @Override
          public void call(List<UnsplashCollection> collections) {
            if (collections.size() > 0) {
              String collectionIds = "";
              for (UnsplashCollection collection : collections) {
                UnsplashCollectionDataHelper.getInstance(MyApp.getContext()).insertCollection
                    (collection);
                UnsplashPictureDataHelper.getInstance(MyApp.getContext()).insertPicture(collection
                    .getCover());
                UnsplashUserDataHelper.getInstance(MyApp.getContext()).insertUser(collection
                    .getUser());
                collectionIds += collection.getId() + ",";
              }
              UnsplashCollectionDataHelper.getInstance(MyApp.getContext())
                  .updateCollectionRelatedCollections(collectionId, collectionIds.substring(0,
                      collectionIds.length() - 1));
            }
          }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPagePictureFromDB(int page, int pageNum,
                                           Subscriber<List<UnsplashPicture>> subscriber) {
    return Observable.just(UnsplashPictureDataHelper.getInstance(MyApp.getContext())
        .queryPagePictures(page, pageNum))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getPageCollectionsFromDB(int page, int pageNum,
                                               Subscriber<List<UnsplashCollection>> subscriber) {
    return Observable.just(UnsplashCollectionDataHelper.getInstance(MyApp.getContext())
        .queryPageCollections(page, pageNum))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription getUserPagePictures(String userName, final int page, final int pageNum,
                                          Subscriber<List<UnsplashPicture>> subscriber) {
    return Observable.
        just(UnsplashUserDataHelper.getInstance(MyApp.getContext()).queryUserByUserName(userName))
        .map(new Func1<UnsplashUser, List<UnsplashPicture>>() {
          @Override
          public List<UnsplashPicture> call(UnsplashUser unsplashUser) {
            List<UnsplashPicture> unsplashPictures = new ArrayList<>();
            if (unsplashUser != null) {
              unsplashPictures.addAll(UnsplashPictureDataHelper.getInstance(MyApp.getContext())
                  .queryUserPagePictures(unsplashUser.getId(), page, pageNum));
            }
            return unsplashPictures;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription collect(final String id, final int type, Subscriber<Integer> subscriber) {
    return Observable.just(id)
        .map(new Func1<String, Integer>() {
          @Override
          public Integer call(String s) {
            MyCollectDataHelper myCollectDataHelper = MyCollectDataHelper.getInstance(MyApp
                .getContext());
            if (myCollectDataHelper.hasCollectAlready(id)) {
              return MyCollectContract.CODE_INSERT_ALREADY;
            } else {
              myCollectDataHelper.insertMyCollect(id, type);
              return MyCollectContract.CODE_INSERT_SUCCESS;
            }
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription deleteMyCollect(final String collectId, Subscriber<String> subscriber) {
    return Observable.just(collectId)
        .doOnNext(new Action1<String>() {
          @Override
          public void call(String s) {
            MyCollectDataHelper.getInstance(MyApp.getContext()).deleteMyCollect(collectId);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription queryCollectCollections(Subscriber<List<UnsplashCollection>> subscriber) {
    return Observable.just(MyCollectDataHelper.getInstance(MyApp.getContext())
        .queryMyCollectByType(MyCollectContract.TYPE_COLLECTION))
        .map(new Func1<List<MyCollect>, List<UnsplashCollection>>() {
          @Override
          public List<UnsplashCollection> call(List<MyCollect> myCollects) {
            List<UnsplashCollection> collections = new ArrayList<UnsplashCollection>();
            UnsplashCollectionDataHelper dataHelper = UnsplashCollectionDataHelper.getInstance
                (MyApp.getContext());
            for (MyCollect myCollect : myCollects) {
              collections.add(dataHelper.queryCollectionById(myCollect.getCollectId()));
            }
            return collections;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription queryCollectPictures(Subscriber<List<UnsplashPicture>> subscriber) {
    return Observable.just(MyCollectDataHelper.getInstance(MyApp.getContext())
        .queryMyCollectByType(MyCollectContract.TYPE_PICTURE))
        .map(new Func1<List<MyCollect>, List<UnsplashPicture>>() {
          @Override
          public List<UnsplashPicture> call(List<MyCollect> myCollects) {
            List<UnsplashPicture> collections = new ArrayList<>();
            UnsplashPictureDataHelper dataHelper = UnsplashPictureDataHelper.getInstance
                (MyApp.getContext());
            for (MyCollect myCollect : myCollects) {
              collections.add(dataHelper.queryPictureById(myCollect.getCollectId()));
            }
            return collections;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription downloadPicture(final String url, final String photoId, Subscriber<Integer>
      subscriber) {
    return Observable.just(photoId).map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        MyDownloadDataHelper dataHelper = MyDownloadDataHelper.getInstance(MyApp.getContext());
        if (dataHelper.downloadAlready(photoId)) {
          return Constants.CODE_PICTURE_DOWNLOAD_ALREADY;
        } else {
          File pictureFile = null;
          String localPath = UserSharedPreferences.getInstance(MyApp.getContext())
              .getUserPhotoBasePath() + "/" + photoId + ".png";
          try {
            File file = new File(localPath);
            if (!file.exists()) {
              file.createNewFile();
            }
            pictureFile = Glide.with(MyApp.getContext()).load(url).downloadOnly(Target
                .SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            FileUtils.copyFileUsingFileChannels(pictureFile, file);
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (pictureFile != null) {
            MyDownloadDataHelper.getInstance(MyApp.getContext()).insertMyDownload(photoId,
                localPath);
            return Constants.CODE_PICTURE_DOWNLOAD_SUCCESS;
          } else {
            return Constants.CODE_PICTURE_DOWNLOAD_SUCCESS;
          }
        }
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription queryDownloadList(Subscriber<List<MyDownload>> subscriber) {
    return Observable.just(MyDownloadDataHelper.getInstance(MyApp.getContext()).queryMyDownload())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }

  public Subscription deleteDownloadPicture(MyDownload myDownload,
                                            Subscriber<Integer> subscriber) {
    return Observable.just(myDownload)
        .map(new Func1<MyDownload, Integer>() {
          @Override
          public Integer call(MyDownload myDownload) {
            MyDownloadDataHelper.getInstance(MyApp.getContext()).deleteMyDownload(myDownload
                .getPhotoId());
            File file = new File(myDownload.getLocalAddress());
            if (file.exists()) {
              file.delete();
            }
            return Constants.DELETE_DOWNLOAD_SUCCESS;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);
  }
}

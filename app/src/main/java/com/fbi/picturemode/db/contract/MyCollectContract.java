package com.fbi.picturemode.db.contract;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollectContract {

  public static final String ID = "ID";
  public static final String COLLECT_ID = "COLLECT_ID";
  public static final String TYPE = "TYPE";
  public static final String FLAG = "FLAG";
  public static final String COLLECT_TIME = "COLLECT_TIME";


  public static final int TYPE_PICTURE = 0;
  public static final int TYPE_COLLECTION = 1;

  public static final int FLAG_NORMAL = 0;
  public static final int FLAG_DELETED = 1;

  public static final int CODE_INSERT_ALREADY = 1;
  public static final int CODE_INSERT_SUCCESS = 2;


}

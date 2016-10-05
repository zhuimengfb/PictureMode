package com.fbi.picturemode.entity;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class UserItem implements Serializable {

  private String title;
  private String subTitle;
  private int iconRes;
  private Class clazz;

  public UserItem(String title, String subTitle, int iconRes, Class clazz) {
    this.title = title;
    this.subTitle = subTitle;
    this.iconRes = iconRes;
    this.clazz = clazz;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public int getIconRes() {
    return iconRes;
  }

  public void setIconRes(int iconRes) {
    this.iconRes = iconRes;
  }

  public Class getClazz() {
    return clazz;
  }

  public void setClazz(Class clazz) {
    this.clazz = clazz;
  }
}

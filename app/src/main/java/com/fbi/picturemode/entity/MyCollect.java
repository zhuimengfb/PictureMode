package com.fbi.picturemode.entity;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollect implements Serializable {

  private int id;
  private String collectId;
  private int type;
  private int flag;
  private long collectTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCollectId() {
    return collectId;
  }

  public void setCollectId(String collectId) {
    this.collectId = collectId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public long getCollectTime() {
    return collectTime;
  }

  public void setCollectTime(long collectTime) {
    this.collectTime = collectTime;
  }
}

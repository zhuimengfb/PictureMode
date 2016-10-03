package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashCollection implements Serializable {

  private int id;
  private String title;
  @JsonProperty("published_at")
  private Date publishTime;
  private boolean curated;
  @JsonProperty("cover_photo")
  private UnsplashPicture cover;

  private UnsplashUser user;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(Date publishTime) {
    this.publishTime = publishTime;
  }

  public boolean isCurated() {
    return curated;
  }

  public void setCurated(boolean curated) {
    this.curated = curated;
  }

  public UnsplashPicture getCover() {
    return cover;
  }

  public void setCover(UnsplashPicture cover) {
    this.cover = cover;
  }

  public UnsplashUser getUser() {
    return user;
  }

  public void setUser(UnsplashUser user) {
    this.user = user;
  }
}

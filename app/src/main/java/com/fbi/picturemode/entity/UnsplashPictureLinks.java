package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashPictureLinks implements Serializable {

  private String raw;
  private String full;
  private String regular;
  private String small;
  private String thumb;

  public String getRaw() {
    return raw;
  }

  public void setRaw(String raw) {
    this.raw = raw;
  }

  public String getFull() {
    return full;
  }

  public void setFull(String full) {
    this.full = full;
  }

  public String getRegular() {
    return regular;
  }

  public void setRegular(String regular) {
    this.regular = regular;
  }

  public String getSmall() {
    return small;
  }

  public void setSmall(String small) {
    this.small = small;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }
}
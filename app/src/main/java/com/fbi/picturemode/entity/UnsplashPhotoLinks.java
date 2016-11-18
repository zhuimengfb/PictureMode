package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashPhotoLinks implements Serializable{

  private String self;
  private String html;
  private String download;
  @JsonProperty("download_location")
  private String downloadLocation;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public String getDownload() {
    return download;
  }

  public void setDownload(String download) {
    this.download = download;
  }

  public String getDownloadLocation() {
    return downloadLocation;
  }

  public void setDownloadLocation(String downloadLocation) {
    this.downloadLocation = downloadLocation;
  }
}

package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 18/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashSearchPhoto implements Serializable {

  private int total;
  @JsonProperty("total_pages")
  private int totalPages;
  @JsonProperty("results")
  private List<UnsplashPicture> pictures;


  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<UnsplashPicture> getPictures() {
    return pictures;
  }

  public void setPictures(List<UnsplashPicture> pictures) {
    this.pictures = pictures;
  }
}

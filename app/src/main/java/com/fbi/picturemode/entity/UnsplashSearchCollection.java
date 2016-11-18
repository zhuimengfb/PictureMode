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
public class UnsplashSearchCollection implements Serializable {

  private int total;
  @JsonProperty("total_pages")
  private int totalPages;
  @JsonProperty("results")
  private List<UnsplashCollection> collections;


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

  public List<UnsplashCollection> getCollections() {
    return collections;
  }

  public void setCollections(List<UnsplashCollection> collections) {
    this.collections = collections;
  }
}

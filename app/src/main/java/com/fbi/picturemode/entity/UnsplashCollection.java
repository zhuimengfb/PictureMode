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
  private String description;

  @JsonProperty("published_at")
  private Date publishTime;
  @JsonProperty("total_photos")
  private int totalPhotos;
  private boolean curated;
  private boolean featured;
  @JsonProperty("private")
  private boolean isPrivate;
  @JsonProperty("share_key")
  private String shareKey;
  @JsonProperty("cover_photo")
  private UnsplashPicture cover;

  private UnsplashUser user;

  private String photos;

  private String relatedCollections;

  @JsonProperty("links")
  private UnsplashCollectionLinks collectionLinks;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(Date publishTime) {
    this.publishTime = publishTime;
  }

  public int getTotalPhotos() {
    return totalPhotos;
  }

  public void setTotalPhotos(int totalPhotos) {
    this.totalPhotos = totalPhotos;
  }

  public boolean isCurated() {
    return curated;
  }

  public void setCurated(boolean curated) {
    this.curated = curated;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  public String getShareKey() {
    return shareKey;
  }

  public void setShareKey(String shareKey) {
    this.shareKey = shareKey;
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

  public String getPhotos() {
    return photos;
  }

  public void setPhotos(String photos) {
    this.photos = photos;
  }

  public String getRelatedCollections() {
    return relatedCollections;
  }

  public void setRelatedCollections(String relatedCollections) {
    this.relatedCollections = relatedCollections;
  }

  public UnsplashCollectionLinks getCollectionLinks() {
    return collectionLinks;
  }

  public void setCollectionLinks(UnsplashCollectionLinks collectionLinks) {
    this.collectionLinks = collectionLinks;
  }
}

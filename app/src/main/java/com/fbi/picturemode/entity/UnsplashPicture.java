package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/2/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashPicture implements Serializable {

  private String id;
  private int width;
  private int height;
  private String color;

  private int likes;
  private int downloads;
  @JsonProperty("created_at")
  private Date createdTime;
  @JsonProperty("like_by_user")
  private boolean likeByUser;

  @JsonProperty("urls")
  private UnsplashPictureLinks unsplashPictureLinks;
  @JsonProperty("user")
  private UnsplashUser unsplashUser;

  @JsonProperty("exif")
  private UnsplashExif exifInfo;
  @JsonProperty("location")
  private UnsplashLocation location;

  @JsonProperty("current_user_collections")
  private List<UnsplashCollection> collections;

  @JsonProperty("categories")
  private List<UnsplashCategory> categories;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public int getDownloads() {
    return downloads;
  }

  public void setDownloads(int downloads) {
    this.downloads = downloads;
  }

  public boolean isLikeByUser() {
    return likeByUser;
  }

  public void setLikeByUser(boolean likeByUser) {
    this.likeByUser = likeByUser;
  }

  public UnsplashPictureLinks getUnsplashPictureLinks() {
    return unsplashPictureLinks;
  }

  public void setUnsplashPictureLinks(UnsplashPictureLinks unsplashPictureLinks) {
    this.unsplashPictureLinks = unsplashPictureLinks;
  }

  public UnsplashUser getUnsplashUser() {
    return unsplashUser;
  }

  public void setUnsplashUser(UnsplashUser unsplashUser) {
    this.unsplashUser = unsplashUser;
  }

  public UnsplashExif getExifInfo() {
    return exifInfo;
  }

  public void setExifInfo(UnsplashExif exifInfo) {
    this.exifInfo = exifInfo;
  }

  public UnsplashLocation getLocation() {
    return location;
  }

  public void setLocation(UnsplashLocation location) {
    this.location = location;
  }

  public List<UnsplashCollection> getCollections() {
    return collections;
  }

  public void setCollections(List<UnsplashCollection> collections) {
    this.collections = collections;
  }

  public List<UnsplashCategory> getCategories() {
    return categories;
  }

  public void setCategories(List<UnsplashCategory> categories) {
    this.categories = categories;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }
}

package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashUser implements Serializable {
  private String id;
  @JsonProperty("username")
  private String userName;
  private String name;
  @JsonProperty("portfolio_url")
  private String portfolioUrl;
  private String bio;
  private String location;
  @JsonProperty("total_likes")
  private int totalLikes;
  @JsonProperty("total_photos")
  private int totalPhotos;
  @JsonProperty("total_collections")
  private int totalCollections;
  @JsonProperty("profile_image")
  private UnsplashUserProfileLinks userProfileImage;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPortfolioUrl() {
    return portfolioUrl;
  }

  public void setPortfolioUrl(String portfolioUrl) {
    this.portfolioUrl = portfolioUrl;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getTotalLikes() {
    return totalLikes;
  }

  public void setTotalLikes(int totalLikes) {
    this.totalLikes = totalLikes;
  }

  public int getTotalPhotos() {
    return totalPhotos;
  }

  public void setTotalPhotos(int totalPhotos) {
    this.totalPhotos = totalPhotos;
  }

  public int getTotalCollections() {
    return totalCollections;
  }

  public void setTotalCollections(int totalCollections) {
    this.totalCollections = totalCollections;
  }

  public UnsplashUserProfileLinks getUserProfileImage() {
    return userProfileImage;
  }

  public void setUserProfileImage(UnsplashUserProfileLinks userProfileImage) {
    this.userProfileImage = userProfileImage;
  }
}

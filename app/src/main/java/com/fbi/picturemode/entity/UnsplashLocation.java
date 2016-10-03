package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashLocation implements Serializable {

  private String city;
  private String country;
  private UnsplashLatLng position;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public UnsplashLatLng getPosition() {
    return position;
  }

  public void setPosition(UnsplashLatLng position) {
    this.position = position;
  }
}

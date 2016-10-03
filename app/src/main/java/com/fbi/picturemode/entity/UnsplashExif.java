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
public class UnsplashExif implements Serializable{

  private String make;
  private String model;
  @JsonProperty("exposure_time")
  private String exposureTime;
  private String aperture;
  @JsonProperty("focal_length")
  private String focalLength;
  private int iso;


  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getExposureTime() {
    return exposureTime;
  }

  public void setExposureTime(String exposureTime) {
    this.exposureTime = exposureTime;
  }

  public String getAperture() {
    return aperture;
  }

  public void setAperture(String aperture) {
    this.aperture = aperture;
  }

  public String getFocalLength() {
    return focalLength;
  }

  public void setFocalLength(String focalLength) {
    this.focalLength = focalLength;
  }

  public int getIso() {
    return iso;
  }

  public void setIso(int iso) {
    this.iso = iso;
  }
}

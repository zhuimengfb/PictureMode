package com.fbi.picturemode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashLatLng implements Serializable {
  private double latitude;
  private double longitude;
}

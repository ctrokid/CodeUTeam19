package com.google.codeu.data;

public class Location {
  private String description;
  private String title;
  private float lat;
  private float lng;

  /**
   *
   * @param description Holds description of the location.
   * @param lat Holds the latitude of the location.
   * @param lng Holds the longitude of the location.
   */
  public Location(String title, String description, float lat, float lng) {
    this.title = title;
    this.description = description;
    this.lat = lat;
    this.lng = lng;
  }

  public float getLat() {
    return lat;
  }

  public float getLng() {
    return lng;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() { return title; }
}

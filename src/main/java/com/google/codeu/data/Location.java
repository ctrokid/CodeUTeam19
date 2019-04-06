package com.google.codeu.data;

public class Location {
  private String description;
  private String title;
  private double lat;
  private double lng;

  /**
   *
   * @param description Holds description of the location.
   * @param lat Holds the latitude of the location.
   * @param lng Holds the longitude of the location.
   */
  public Location(String title, String description, double lat, double lng) {
    this.title = title;
    this.description = description;
    this.lat = lat;
    this.lng = lng;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getName() {
    return description;
  }

  public String getTitle() { return title; }
}

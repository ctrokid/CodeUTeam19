package com.google.codeu.data;

import java.util.UUID;

public abstract class ItemSchedule {
  protected UUID id;
  protected String startTime;
  protected String endTime;
  protected String description;
  protected Location location;
  protected String title;

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public Location getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }

  public UUID getId() { return id; }

  public String getTitle() { return title; }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}

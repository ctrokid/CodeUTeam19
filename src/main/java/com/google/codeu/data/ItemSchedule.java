package com.google.codeu.data;

import java.util.UUID;

public abstract class ItemSchedule {
  protected UUID id;
  protected Time startTime;
  protected Time endTime;
  protected String description;
  protected Location location;

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public Location getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }

  public UUID getId() { return id; }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

}

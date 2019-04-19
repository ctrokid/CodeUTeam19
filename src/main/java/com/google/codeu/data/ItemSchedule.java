package com.google.codeu.data;

import java.util.UUID;

public abstract class ItemSchedule {
  protected UUID id;
  protected long startTime;
  protected long endTime;
  protected String description;
  protected Location location;
  protected String creator;

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public Location getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }

  public UUID getId() { return id; }

  public String getCreator() { return creator; }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}

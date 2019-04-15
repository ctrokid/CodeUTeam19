package com.google.codeu.data;

public class MvpConcreteEvent {
  private String description;
  private String location;
  private String startTime;
  private String endTime;

  /**
   * Create a new Event.
   *
   * @param description What is the task? E.g. Programming Assignment
   * @param location    Where will the ask occur?
   * @param startTime   the start time of the event
   * @param endTime     the end time of the event
   */
  public MvpConcreteEvent(String description, String location, String startTime, String endTime) {
    this.description = description;
    this.location = location;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public String getDescription() {
    return description;
  }

  public String getLocation() {
    return location;
  }
}


package com.google.codeu.data;

import java.util.List;

public class Event implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private int priorityLevel;
  private String description;
  private List<User> collaborators;
  private String location;

  /**
   * Create a new Event.
   * @param startTime the start time of the event
   * @param endTime the end time of the event
   * @param priorityLevel the priority level of the event
   */
  public Event(Time startTime, Time endTime, int priorityLevel) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
  }
}

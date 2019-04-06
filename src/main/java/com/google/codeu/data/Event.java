package com.google.codeu.data;

import java.util.List;

public class Event implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private int priorityLevel;
  private String description;
  private List<User> collaborators;

  private Location location;


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

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public int getPriorityLevel() {
    return priorityLevel;
  }

  public String getDescription() {
    return description;
  }

  public List<User> getCollaborators() {
    return collaborators;
  }

  public Location getLocation() {
    return location;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public void setPriorityLevel(int priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCollaborators(List<User> collaborators) {
    this.collaborators = collaborators;
  }
  
  public void setLocation(Location location) {
    this.location = location;
  }
}

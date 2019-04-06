package com.google.codeu.data;

import java.util.List;

public class Task implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private int priorityLevel;
  private String description;
  private List<User> collaborators;
  private Location location;
  private boolean completed;

  /**
   * Create a new Task.
   * @param startTime start time of the task
   * @param endTime end time of the task
   * @param priorityLevel priority level of the task
   */
  public Task(Time startTime, Time endTime, int priorityLevel) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public int getPriorityLevel() {
    return priorityLevel;
  }

  public void setPriorityLevel(int priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<User> getCollaborators() {
    return collaborators;
  }

  public void setCollaborators(List<User> collaborators) {
    this.collaborators = collaborators;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {

    this.location = location;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}

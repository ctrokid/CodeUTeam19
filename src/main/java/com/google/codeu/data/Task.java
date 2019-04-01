package com.google.codeu.data;

import java.util.List;

public class Task implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private int priorityLevel;
  private String description;
  private List<User> collaborators;
  private String location;
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
}

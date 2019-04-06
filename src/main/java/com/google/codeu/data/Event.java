package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Event extends ItemSchedule {
  private int priorityLevel;
  private List<User> collaborators;

  /**
   * Create a new Event.
   * @param startTime the start time of the event
   * @param endTime the end time of the event
   * @param priorityLevel the priority level of the event
   */
  public Event(Time startTime, Time endTime, int priorityLevel) {
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
  }

  public Event(UUID id, Time startTime, Time endTime, int priorityLevel) {
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
  }

  public int getPriorityLevel() {
    return priorityLevel;
  }

  public List<User> getCollaborators() {
    return collaborators;
  }

  public void setPriorityLevel(int priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public void setCollaborators(List<User> collaborators) {
    this.collaborators = collaborators;
  }
  

}

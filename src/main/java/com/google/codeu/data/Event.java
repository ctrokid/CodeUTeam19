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
  public Event(String creator, long startTime, long endTime, int priorityLevel) {
    this.creator = creator;
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
    description = "";
  }

  public Event(String creator, UUID id, long startTime, long endTime, int priorityLevel) {
    this.creator = creator;
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
    description = "";
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

package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Event extends ItemSchedule {
  private String priorityLevel;
  private List<User> collaborators;

  /**
   * Create a new Event.
   * @param startTime the start time of the event
   * @param endTime the end time of the event
   * @param priorityLevel the priority level of the event
   */
  public Event(String title, String startTime, String endTime, String priorityLevel) {
    this.title = title;
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;
    this.priorityLevel = priorityLevel;
    description = "";
  }


  public String getPriorityLevel() {
    return priorityLevel;
  }

  public List<User> getCollaborators() {
    return collaborators;
  }

  public void setPriorityLevel(String priorityLevel) {
    this.priorityLevel = priorityLevel;
  }

  public void setCollaborators(List<User> collaborators) {
    this.collaborators = collaborators;
  }
  

}

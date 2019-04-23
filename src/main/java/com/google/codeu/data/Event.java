package com.google.codeu.data;

import java.util.UUID;

public class Event extends ItemSchedule {

  /**
   * Create a new Event.
   * @param startTime the start time of the event
   * @param endTime the end time of the event
   * @param description the priority level of the event
   */
  public Event(String title, String startTime, String endTime, String description) {
    this.title = title;
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
  }

  public String getTitle(){
    return this.title;
  }

  public String getStartTime(){
    return this.startTime;
  }

  public String getEndTime(){
    return this.endTime;
  }

  public String getDescription(){
    return this.description;
  }


}

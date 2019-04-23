package com.google.codeu.data;

import java.util.ArrayList;

/**
 * This class contains a schedule for an individual user, and contains methods to manage a schedule.
 */
public class ScheduleManager {
  private ArrayList<ItemSchedule> schedule;
  /**
   * Create a new ScheduleManager.
   */
  public ScheduleManager() {
    this.schedule = new ArrayList<>();
  }

  public void addToSchedule(ItemSchedule scheduleItem){
    schedule.add(scheduleItem);
  }

  public ArrayList<ItemSchedule> getSchedule() {
    return schedule;
  }

  //TODO this should return a calandar API compatible event
  public Event exportScheduleToGoogleCalendar(){
    Event calendarEvent = new Event("",
            "", "", "");
    return calendarEvent;
  }

}

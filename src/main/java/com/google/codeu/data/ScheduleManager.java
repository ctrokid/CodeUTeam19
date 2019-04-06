package com.google.codeu.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a schedule for an individual user, and contains methods to manage a schedule.
 */
public class ScheduleManager {
  private Schedule schedule;
  /**
   * Create a new ScheduleManager.
   */
  public ScheduleManager() {

  }

  public Schedule Schedule() {
    return schedule;
  }


  public List<ScheduleItem> getSchedule() {
    return schedule;
  }

  public void setSchedule(List<ScheduleItem> schedule) {
    this.schedule = schedule;
  }
}

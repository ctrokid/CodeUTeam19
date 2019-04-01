package com.google.codeu.data;

import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
  private List<ScheduleItem> schedule;

  /**
   * Create a new ScheduleManager.
   */
  public ScheduleManager() {
    this.schedule = new ArrayList<>();
  }

  public List<ScheduleItem> getSchedule() {
    return schedule;
  }

  public void setSchedule(List<ScheduleItem> schedule) {
    this.schedule = schedule;
  }
}

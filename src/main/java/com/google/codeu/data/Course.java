package com.google.codeu.data;

import java.util.List;

public class Course implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private List<String> daysOfWeek;
  private String description;
  private String grade;
  private String location;
  private List<Assignment> assignments;

  public Course(Time startTime, Time endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }


}

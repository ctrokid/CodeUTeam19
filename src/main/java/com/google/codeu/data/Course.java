package com.google.codeu.data;

import java.util.List;

public class Course implements ScheduleItem {
  private Time startTime;
  private Time endTime;
  private List<Days> daysOfWeek;
  private String description;
  private String grade;
  private String location;
  private List<Assignment> assignments;

  public Course(Time startTime, Time endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public List<Days> getDaysOfWeek() {
    return daysOfWeek;
  }

  public String getDescription() {
    return description;
  }

  public String getGrade() {
    return grade;
  }

  public String getLocation() {
    return location;
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public void setDaysOfWeek(List<Days> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setAssignments(List<Assignment> assignments) {
    this.assignments = assignments;
  }
}

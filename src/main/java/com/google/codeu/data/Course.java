package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Course extends ItemSchedule {

  private List<Days> daysOfWeek;
  private String grade;
  private List<Assignment> assignments;

  public Course(String creator, UUID id, long startTime, long endTime) {
    this.creator = creator;
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Course(String creator, long startTime, long endTime) {
    this.creator = creator;
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public List<Days> getDaysOfWeek() {
    return daysOfWeek;
  }

  public String getGrade() {
    return grade;
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public void setDaysOfWeek(List<Days> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setAssignments(List<Assignment> assignments) {
    this.assignments = assignments;
  }
}

package com.google.codeu.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course extends ItemSchedule {

  private List<Days> daysOfWeek;
  private String grade;
  private List<Assignment> assignments;

  public Course(String creator, UUID id, String startTime, String endTime) {
    this.title = creator;
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;

    daysOfWeek = new ArrayList<>();
    assignments = new ArrayList<>();
    description = "";
    grade = "";
  }

  public Course(String creator, String startTime, String endTime) {
    this.title = creator;
    this.id = UUID.randomUUID();
    this.startTime = startTime;
    this.endTime = endTime;

    daysOfWeek = new ArrayList<>();
    assignments = new ArrayList<>();
    description = "";
    grade = "";
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

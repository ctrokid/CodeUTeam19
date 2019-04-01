package com.google.codeu.data;

public class Assignment implements ScheduleItem {
  private String dueDate;
  private Course course;
  private boolean completed;

  public Assignment(String dueDate) {
    this.dueDate = dueDate;
  }
}

package com.google.codeu.data;

public class Assignment {
  private String dueDate;
  private String course;
  private boolean completed;

  public Assignment(String course, String dueDate) {
    this.course = course;
    this.dueDate = dueDate;
  }

  public Assignment(String course, String dueDate, boolean completed) {
    this.course = course;
    this.dueDate = dueDate;
    this.completed = completed;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}

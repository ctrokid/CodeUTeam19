package com.google.codeu.data;

public class Assignment {
  private String dueDate;
  private Course course;
  private boolean completed;

  public Assignment(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}

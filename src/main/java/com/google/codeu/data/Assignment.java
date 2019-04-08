package com.google.codeu.data;

public class Assignment {
  private String dueDate;
  private String course;
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

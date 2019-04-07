package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Task extends Event {
  private boolean completed;

  /**
   * Create a new Task.
   * @param startTime start time of the task
   * @param endTime end time of the task
   * @param priorityLevel priority level of the task
   */
  public Task(long startTime, long endTime, int priorityLevel) {
    super(startTime,endTime,priorityLevel);
  }

  public Task(UUID id, long startTime, long endTime, int priorityLevel) {
    super(id,startTime,endTime,priorityLevel);
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}

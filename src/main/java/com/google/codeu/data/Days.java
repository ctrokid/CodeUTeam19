package com.google.codeu.data;

public enum Days {
  Monday("Monday"), Tuesday("Tuesday"), Wednesday("Wednesday"),
  Thursday("Thursday"), Friday("Friday"), Saturday("Saturday"), Sunday("Sunday");
  
  private String value;

  Days(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
